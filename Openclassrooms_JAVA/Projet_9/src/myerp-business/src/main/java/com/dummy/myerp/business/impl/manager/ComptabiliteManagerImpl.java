package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.dummy.myerp.model.bean.comptabilite.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Comptabilite manager implementation.
 */
@Transactional(propagation = Propagation.MANDATORY)
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================


    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        return getDaoProxy().getComptabiliteDao().getEcritureComptable(pId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) throws FunctionalException, NotFoundException {

        /* Le principe :
                1.  Remonter depuis la persitance la derni??re valeur de la s??quence du journal pour l'ann??e de l'??criture
                    (table sequence_ecriture_comptable)
        */
        Integer vEcritureComptableYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(pEcritureComptable.getDate()));
        SequenceEcritureComptable vRechercheSequence = new SequenceEcritureComptable();
        vRechercheSequence.setJournalCode(pEcritureComptable.getJournal().getCode());
        vRechercheSequence.setAnnee(vEcritureComptableYear);
        SequenceEcritureComptable vExistingSequence = getDaoProxy().getComptabiliteDao().getSequenceByCodeAndAnneeCourante(vRechercheSequence);
        /*        2.  * S'il n'y a aucun enregistrement pour le journal pour l'ann??e concern??e :
                        1. Utiliser le num??ro 1.
                    * Sinon :
                        1. Utiliser la derni??re valeur + 1
        */
        Integer vNumeroSequence;
        if (vExistingSequence == null) vNumeroSequence = 1;
        else vNumeroSequence = vExistingSequence.getDerniereValeur() + 1;
        /*
                3.  Mettre ?? jour la r??f??rence de l'??criture avec la r??f??rence calcul??e (RG_Compta_5)
        */
        String vReference = pEcritureComptable.getJournal().getCode() +
                "-" + vEcritureComptableYear +
                "/" + String.format("%05d", vNumeroSequence);
        pEcritureComptable.setReference(vReference);
        this.updateEcritureComptable(pEcritureComptable);
        /*
                4.  Enregistrer (insert/update) la valeur de la s??quence en persistance
                    (table sequence_ecriture_comptable)
        */
        SequenceEcritureComptable vNewSequence = new SequenceEcritureComptable();
        vNewSequence.setJournalCode(pEcritureComptable.getJournal().getCode());
        vNewSequence.setAnnee(vEcritureComptableYear);
        vNewSequence.setDerniereValeur(vNumeroSequence);
        this.upsertSequenceEcritureComptable(vNewSequence);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * V??rifie que l'Ecriture comptable respecte les r??gles de gestion unitaires,
     * c'est ?? dire ind??pendemment du contexte (unicit?? de la r??f??rence, exercice comptable non clotur??...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les r??gles de gestion
     */
    protected void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== V??rification des contraintes unitaires sur les attributs de l'??criture
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'??criture comptable ne respecte pas les r??gles de gestion.",
                                          new ConstraintViolationException(
                                              "L'??criture comptable ne respecte pas les contraintes de validation",
                                              vViolations));
        }

        // ===== RG_Compta_2 : Pour qu'une ??criture comptable soit valide, elle doit ??tre ??quilibr??e
        if (!pEcritureComptable.isEquilibree()) {
            throw new FunctionalException("L'??criture comptable n'est pas ??quilibr??e.");
        }

        // ===== RG_Compta_3 : une ??criture comptable doit avoir au moins 2 lignes d'??criture (1 au d??bit, 1 au cr??dit)
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // On test le nombre de lignes car si l'??criture ?? une seule ligne
        //      avec un montant au d??bit et un montant au cr??dit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
            || vNbrCredit < 1
            || vNbrDebit < 1) {
            throw new FunctionalException(
                "L'??criture comptable doit avoir au moins deux lignes : une ligne au d??bit et une ligne au cr??dit.");
        }

        // v??rifier que l'ann??e dans la r??f??rence correspond bien ?? la date de l'??criture, idem pour le code journal...
        String vDate = new SimpleDateFormat("yyyy").format(pEcritureComptable.getDate());
        if (!pEcritureComptable.getReference().substring(3, 7).equals(vDate))
            throw new FunctionalException(
                    "L'ann??e dans la r??f??rence doit correspondre ?? la date de l'??criture comptable.");
        if (!pEcritureComptable.getReference().substring(0, 2).equals(pEcritureComptable.getJournal().getCode()))
            throw new FunctionalException(
                    "Le code journal dans la r??f??rence doit correspondre au code du journal en question.");
    }


    /**
     * V??rifie que l'Ecriture comptable respecte les r??gles de gestion li??es au contexte
     * (unicit?? de la r??f??rence, ann??e comptable non clotur??...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les r??gles de gestion
     */
    protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La r??f??rence d'une ??criture comptable doit ??tre unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // S'il s'agit d'une nouvelle ??criture, o?? alors si l'??criture remont??e correspond ?? celle ?? v??rifier...
                if (pEcritureComptable.getId() == null
                    || !pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre ??criture comptable existe d??j?? avec la m??me r??f??rence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ??a veut dire qu'on n'a aucune autre ??criture avec la m??me r??f??rence.
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upsertSequenceEcritureComptable(SequenceEcritureComptable pSequence) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().upsertSequenceEcritureComptable(pSequence);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }
}
