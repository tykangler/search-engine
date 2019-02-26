package search.analyzers;

import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.exceptions.NotYetImplementedException;
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {
    // This field must contain the IDF score for every single word in all
    // the documents.
    private IDictionary<String, Double> idfScores;

    // This field must contain the TF-IDF vector for each webpage you were given
    // in the constructor.
    //
    // We will use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

    private IDictionary<URI, Double> normalizedTfIdfScores;

    // Feel free to add extra fields and helper methods.

    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        this.idfScores = this.computeIdfScores(webpages);
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
        for (KVPair<URI, IDictionary<String, Double>> vector : documentTfIdfVectors) {
            normalizedTfIdfScores.put(vector.getKey(), norm(vector.getValue()));
        }
    }

    // Note: this method, strictly speaking, doesn't need to exist. However,
    // we've included it so we can add some unit tests to help verify that your
    // constructor correctly initializes your fields.
    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }

    // Note: these private methods are suggestions or hints on how to structure your
    // code. However, since they're private, you're not obligated to implement exactly
    // these methods: feel free to change or modify these methods however you want. The
    // important thing is that your 'computeRelevance' method ultimately returns the
    // correct answer in an efficient manner.

    /**
     * Return a dictionary mapping every single unique word found
     * in every single document to their IDF score.
     */
    private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
        IDictionary<String, Double> scores = new ChainedHashDictionary<String, Double>();
        int numPages = pages.size();
        for (Webpage page : pages) {
            IList<String> wordsInPage = page.getWords();
            IDictionary<String, Boolean> hasVisited = new ChainedHashDictionary<String, Boolean>();
            for (String word : wordsInPage) {
                if (!hasVisited.containsKey(word)) {
                    hasVisited.put(word, true);
                    scores.put(word, scores.getOrDefault(word, 0.0) + 1);
                }
            }
        }
        for (KVPair<String, Double> counts : scores) {
            scores.put(counts.getKey(), Math.log(numPages / counts.getValue()));
        }
        return scores;
    }

    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) score.
     *
     * The input list represents the words contained within a single document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
        IDictionary<String, Double> tfScores = new ChainedHashDictionary<String, Double>();
        for (String word : words) {
            if (!tfScores.containsKey(word)) {
                tfScores.put(word, 1.0);
            }
            tfScores.put(word, tfScores.get(word) + 1.0);
        }
        for (KVPair<String, Double> counts : tfScores) {
            tfScores.put(counts.getKey(), (counts.getValue() / words.size()));
        }
        return tfScores;
    }

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        IDictionary<URI, IDictionary<String, Double>> tfIdfVectors = 
            new ChainedHashDictionary<URI, IDictionary<String, Double>>();
        for (Webpage page : pages) {
            IDictionary<String, Double> relevance = computeTfScores(page.getWords());
            for (KVPair<String, Double> tfScore : relevance) {
                double relevanceScore = tfScore.getValue() * idfScores.getOrDefault(tfScore.getKey(), 0.0);
                relevance.put(tfScore.getKey(), relevanceScore);
                tfIdfVectors.put(page.getUri(), relevance);
            }
        }
        return tfIdfVectors;
    }

    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query and the
     * URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
        IDictionary<String, Double> tfIdfVector = documentTfIdfVectors.get(pageUri);
        IDictionary<String, Double> queryVector = computeTfScores(query);
        double numerator = 0.0;
        for (KVPair<String, Double> tfScore : queryVector) {
            double queryScore = tfScore.getValue() * idfScores.getOrDefault(tfScore.getKey(), 0.0);
            double docWordScore = tfIdfVector.getOrDefault(tfScore.getKey(), 0.0);
            numerator += queryScore * docWordScore;
            queryVector.put(tfScore.getKey(), queryScore);
        }
        double denominator = normalizedTfIdfScores.get(pageUri) * norm(queryVector);
        return (denominator != 0) ? numerator / denominator : 0.0;
    }

    private double norm(IDictionary<String, Double> vector) {
        double res = 0.0;
        for (KVPair<String, Double> entry : vector) {
            res += entry.getValue() * entry.getValue();
        }
        return Math.sqrt(res);
    }
}
