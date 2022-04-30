package com.dummy.myerp.business.contrat.manager;

import java.util.List;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Interface du manager du package comptabilite.
 */
public interface ComptabiliteManager {

    // ==================== CompteComptable ====================

    /**
     * Renvoie la liste des comptes comptables.
     *
     * @return {@link List}
     */
    List<CompteComptable> getListCompteComptable();


    // ==================== JournalComptable ====================

    /**
     * Renvoie la liste des journaux comptables.
     *
     * @return {@link List}
     */
    List<JournalComptable> getListJournalComptable();


    // ==================== EcritureComptable ====================

    /**
     * Renvoie la liste des écritures comptables.
     *
     * @return {@link List}
     */
    List<EcritureComptable> getListEcritureComptable();

    /**
     * Renvoie l'Écriture Comptable d'id {@code pId}.
     *
     * @param pId l'id de l'écriture comptable
     * @return {@link EcritureComptable}
     * @throws NotFoundException : Si l'écriture comptable n'est pas trouvée
     */
    EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException;

    /**
     * Ajoute une référence à l'écriture comptable.
     * <p>
     *  <strong>RG_Compta_5 : </strong>
     *  La référence d'une écriture comptable est composée du code du journal dans lequel figure l'écriture
     *  suivi de l'année et d'un numéro de séquence (propre à chaque journal) sur 5 chiffres incrémenté automatiquement
     *  à chaque écriture. Le formatage de la référence est : XX-AAAA/#####.
     *  <br>
     *  Ex : Journal de banque (BQ), écriture au 31/12/2016
     *  <pre>BQ-2016/00001</pre>
     * </p>
     *
     * <p><strong>Attention :</strong> l'écriture n'est pas enregistrée en persistance</p>
     * @param pEcritureComptable L'écriture comptable concernée
     */
    void addReference(EcritureComptable pEcritureComptable) throws FunctionalException, NotFoundException;

    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion.
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException;

    /**
     * Insert une nouvelle écriture comptable.
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException;

    /**
     * Met à jour l'écriture comptable.
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException;

    /**
     * Supprime l'écriture comptable d'id {@code pId}.
     *
     * @param pId l'id de l'écriture
     */
    void deleteEcritureComptable(Integer pId);


    // ==================== SequenceEcritureComptable ====================

    /**
     * Insert une nouvelle séquence écriture comptable ou en met une déjà existante à jour.
     *
     * @param pSequence -
     */
    void upsertSequenceEcritureComptable(SequenceEcritureComptable pSequence);
}
