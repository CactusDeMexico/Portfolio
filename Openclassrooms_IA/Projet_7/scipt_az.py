# The script MUST contain a function named azureml_main
# which is the entry point for this module.

# imports up here can be used to
import pandas as pd
import re
import spacy
import string
import os


def preprocess_reviews(reviews):
    REPLACE_NO_SPACE = re.compile("[.;:!\?,\"()\[\]]")
    REPLACE_WITH_SPACE = re.compile("(<br\s*/><br\s*/>)|(\-)|(\/)|(\n)|(')|[0-9]")
    reviews = [REPLACE_NO_SPACE.sub("", line.lower()) for line in reviews]
    reviews = [REPLACE_WITH_SPACE.sub(" ", line) for line in reviews]
    return reviews


def get_lemmatized_text(corpus):
    lem = [' '.join([token.lemma_ for token in nlp(review)]) for review in corpus]
    return lem


def remove_stop_words(corpus, stop_words):
    removed_stop_words = []
    for review in corpus:
        removed_stop_words.append(
            ' '.join([word for word in review.split()
                      if word not in stop_words])
        )
    return removed_stop_words


# The entry point function MUST have two input arguments.
# If the input port is not connected, the corresponding
# dataframe argument will be None.
#   Param<dataframe1>: a pandas.DataFrame
#   Param<dataframe2>: a pandas.DataFrame
def azureml_main(dataframe1=None, dataframe2=None):
    os.system('pip install spacy')
    import spacy
    # Execution logic goes here
    print(f'Input pandas.DataFrame #1: {dataframe1}')
    tweets = dataframe1['tweet'].tolist()

    tweets = preprocess_reviews(tweets)

    sp = string.punctuation
    tweets = list(map(lambda t: ''.join(["" if c.isdigit() else c for c in t]), tweets))
    tweets = list(map(lambda t: ''.join(["" if c in sp else c for c in t]), tweets))
    tweets = list(map(str.lower, tweets))

    nlp = spacy.load('en_core_web_sm')
    tweets = [' '.join([token.lemma_ for token in nlp(review)]) for review in tweets]
    stop_words_ot = ['a', 'about', 'above', 'the', 'after', 'my', 'me', 'our',
                     'your', 'i', 'we', 'you', 'he', 'she', 'they',
                     'their', 'in', 'until', 'before', 'it', 'and', 'at', 'of']

    tweets = remove_stop_words(tweets, stop_words_ot)
    dataframe1['clean_tweet'] = tweets

    # If a zip file is connected to the third input port,
    # it is unzipped under "./Script Bundle". This directory is added
    # to sys.path. Therefore, if your zip file contains a Python file
    # mymodule.py you can import it using:
    # import mymodule

    # Return value must be of a sequence of pandas.DataFrame
    # E.g.
    #   -  Single return value: return dataframe1,
    #   -  Two return values: return dataframe1, dataframe2
    return dataframe1,

