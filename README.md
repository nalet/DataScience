# DataScience

Content from course of Bern University of Applied Sciences by ©Jürgen Vogel

## Glossar

* Natural Language Processing __NLP__
* text = sequence of words __tokens__
* information retrieval __IR__
* token frequency __tf__
* inverse document frequency __idf__
* NamedEntity Recognition __NER__

## Text Analysis Disciplines

* parsing
    * from unstructured to structured data
    * detecting linguistic features
    * spelling and grammar correction

* information retrieval
    * (keyword) search
    * named entity recognition

* clustering
    * similarity grouping
    * recommendation
    * taxonomy creation

* classification
    * document type, categorization
    * author
    * compliance check
    * language detection
    * sentiment analysis

* summarization

* translation

* knowledge creation (reasoning)
    * patterns
    * prediction

* dialog(conversational agents)
    * question answering
    * helpdesk, shopping, …

## Words

* Morphology
    * studies how words are built up from smaller units called morphemes

* Morphemes
    1. Stems: core meaning-bearing units
    2. Affixes: adhere to stems changing meanings and grammatical functions
        * prefix or suffix/postfix, e.g.,
        * foxes [fox noun plural]
        * talking [talk verb gerundium]
        * clueless[adjective form of the noun clue]
        * unnecessary [opposite]

* Word class
    * verb, noun, adjective, …
    * the way that stems and affixes combine is based on the stem’s word class

* English
    * Nouns: markers for plural and possessive
    * Verbs: markers appropriate to the tense of the verb
    * BUT irregulars: mouse/mice, goose/geese, go/went, fly/flew, …

### Initial Stages of Text Processing

* Tokenization
    * cut character sequence into words (tokens)
        * deal with John’s, a state-of-the-art solution

* Stop word elimination
    * may omit very common words (or not)
        * the, a, to, of

* Normalization
    * map different word forms
        * U.S.A.and USA should make no difference

* Lemmatizationor Stemming
    * may match different forms to a root (stem)
        * authorize andauthorization to author

### Tokenization
Tokenization: breaking up a document into tokens (words)
* Token is a sequence of characters
    * Input: Friends, Romans and Countrymen.
    * Output: 4 Tokens
        * Friends
        * Romans
        * and
        * Countrymen

* Heuristic: break at whitespaces and punctuation
    * often called "whitespace tokenizer"

### Stop Words
* Stop words
    * have little semantic content: the, a, and, to, be, …
    * are frequent: ~30% of postings for top 30 words

* With a stop word list, you exclude these from further analysis
    * exclude meaningless content
    * save some space

* But depending on the application, it is better/necessary to keep them
    * e.g., in information retrieval (IR) you need them for
        * phrase queries: “King of Denmark”
        * various song titles, etc.: “Let it be”, “To be or not to be”
        * “relational” queries: “flights to London”    

### Normalization

Information Retrieval
* We want to match
    * U.S.A.and USA
    * bank, banker, and banking

* “Normalize” words into the same form
    * e.g., in IR, for both query and dictionary

* Result are terms
    * a term is a (normalized) word type

* Method: define equivalence classes of terms by
    * deleting periods to form a term
        * U.S.A., USA -> USA
    * deleting hyphens to form a term
        * anti-discriminatory, antidiscriminatory -> antidiscriminatory

* But: ambiguity!
    * longstanding Google example: query C.A.T.
    * #1 result is for cats, not Caterpillar Inc.

### Case Folding

* reduce all letters to lower case

* widely used in IR
    * exception: upper case in mid-sentence?
        * e.g., General Motors
        * Fedvs. fed
        * CATvs. cat
    * often best to lower case everything, since users will use lowercase regardless of ‘correct’ capitalization…

* BUT for sentiment analysis, MT, information extraction, case is helpful:
    * US versus us is important        

### Lemmatization

* reduce inflectional/variant forms to base form, e.g.,
    * am, are,is →be
    * car, cars, car's, cars'→car
    * the boy's cars are different colors→the boy car be different color

* implies doing “proper” reduction to dictionary headword form
    * complex morphological (linguistic) analysis required

### Stemming
* “Stemming” is crude affix chopping
    * reduce terms to their “roots”
    * language dependent
    * e.g., automate(s), automatic, automation all reduced to automat

`for example compressed and compression are both accepted as equivalent to compress.`

converts to

`for examplcompress andcompress arboth accept as equivalto compress`

* much simpler & faster than proper lemmatization
    * and evaluation results for IR show at most modest benefits of lemmatization vs. stemming

## Text Comparison
* Comparing2 (or more) texts (character sequences) is required in many use cases, e.g.,
    * IR: matchingqueries to documents (or the index)
    * spelling correction
    * versioncontrol
    * machinetranslation
    * plagiarismdetection
    * ...

* Equalityis relatively simple: pairwise character equality
    * But often we are rather interested in: how similar are 2 texts
        * Simliarity(text1, text2) in [0, 1] where 1 is "equal" and 0 is "completely different"

* Topics
    * Document similarity
    * Word similarity

## Document Similarity
* use cases
    * IR
    * recommender
    * plagiarism checker
    * …

* given 2 documents D1 and D2, are they similar?
    * sim(D1, D2) [0,1]
    * where 0 is unrelated and 1 is identical
    
### Vector Space Model
* Idea: Document D can be interpreted mathematically as a vector
    * given: an ordered dictionary V of tokens
        * e.g., (cat, dog, eats, mouse)

    * D can now be represented as a vector with respect to V
        * e.g., document "cateatsmouse": (1, 0, 1, 1)

    * so we have a |V|-dimensional vector space
        * tokens are axes of the space
        * documents are points or vectors in this space

    * for regular language, V is large
        * 500.000 –600.000 distinct words in english (without specialized terminologies)
        * so specific documents are very sparse vectors -most entries are zero

* Document similarity = vector similarity
    * Euclidean Distance
    * for documents P = (p1, p2, … pn) and Q = (q1, q2, …, qn)

### Token Weights
* in the simplest case, the document vector indicates whether a token is present (1) or not (0)

* but in most texts, some tokens are more characteristic/important than others
    * remember TFD
    * idea: count token frequencies
    * dictionary without stop words

* but: Euclidean distance does not work anymore
    * take a document D and append it to itself; call this document D′
    * “semantically” D and D′have the same content
    * but the Euclidean distance between D and D' can be quite large

* angle distance
    * measure angle between the vectors of D1 and D2
    * angles are measured in degrees in [0, 360]
    * better: cosine, which is in [0, 1]

* token frequency tf(t, D) of token t in document D is defined as the number of times that t occurs in D

### TF
* raw token frequency is not what we want:
    * a token that occurs 10 times in a document is more characteristic for D than if it occurred only 1 time
    * but not 10 times more important
        * i.e., importance does not increase proportionally with token frequency

* idea: tflog(t, D) = (1 + log tf(t, D)) for tf(t, D) > 0, 0 else
    * 0 →0, 1 →1, 2 →1.3, 10 →2, 1000 →4, …

### IDF
* Rare tokens are more characteristic than frequent tokens
    * consider a token which is generally rare (e.g., arachnocentric)
    * distinctive if a document contains such a rare token
    * 2 documents, which both contain a rare token, are likely to be similar
    * thus we want a high weight for rare tokens

* Frequent tokens are less informative than rare tokens
    * consider tokens that are frequent (e.g., high, increase, other)
    * still relevant for the similarity measure, but not highly so
    * for frequent terms, we want positive weights
        * but lower weights than for rare terms

* document frequency (df)
    * df(t): the number of documents that contain t
    * for N documents: 0 ≤ df(t) ≤ N
    * df(t) is an inverse measure of the informativenessof t

* inverse document frequency (idf)
    * idf(t) = log (N/df(t))
    * use log to “dampen” the effect of idf

### TF-IDF Weighting

* now we have 2 weights
    * tfmeasures how often a token occurs in a single document
        * increases with the number of occurrences within a document
    * idfmeasures how often a token occurs in all documents
    * increases with the rarity of the token

* tf-idfweight of a token is the product of its tfand idfweights
    * tf-idf(t, D) = (1 + log tf(t, D)) log (N/df(t))

* sim (D1, D2)
    * use tf-idfweights as token value for document vectors
    * then calculate cosine similarity for document similarity

## Using Document Similarity for Clustering

### Clustering Documents
* aim to organize a set of documents into groups, e.g., cluster
    * news articles based on topics (e.g., Google News)
    * search results based on similar content

* based on some similarity measure
    * all documents within a cluster should be similar and documents in different clusters  dissimilar
    * i.e., clustering is a generalization of 2 document comparison
    * usually some variation of cosine similarity of TF-IDF document vectors

* may also produce a description for each cluster discovered
    * i.e., a representative document, a label, several labels, or a summary

* may also built a hierarchy (relations) between clusters

    * often based on unsupervised machine learning
    * i.e., can run fully automated w/o training
    * e.g.,
        * Latent DirichletAllocation
        * K-Means

* may need to be fine-tuned via parameters
    * e.g., number of clusters

* tend to be computationally expensive

### Document Clustering via Partitioning
* construct a partition of ndocuments into a set of Kclusters
    * given: a set of documents and the number K
    * find: a partition of Kclusters that optimizes the partitioning criterion
        * optimal?
            * intractable for many objective functions
            * ergo, exhaustively enumerate all partitions

* effective heuristic methods: K-means and K-medoidsalgorithms

### K-Means
* Idea
    * Assumes documents are real-valued vectors
    * Clusters based on centroids of points in a cluster, c:
        * reassignment of documents to clusters is based on distance to the current cluster centroids

* Algorithm
    * Select K random docs {s1, s2,… sK} as seeds
    * Until clustering converges (or other stopping criterion):
        * for each doc di
            * assign dito the cluster cjsuch that dist(xi, sj) is minimal
        * for each cluster cj
            * sj= μ(cj)

* termination conditions
    * fixed number of iterations
    * partition unchanged
    * centroid positions unchanged

## Comparing 2 Words
* Equality
    * two words are equal or not
    * may consider inflection: the carvs. the car’sdoor

* Synonymy
    * two words are either synonymous or not
    * car and automobileare synonyms

* Similarity(distance)
    * two words are more similar if they share more features of meaning
    * carand bicycle are similar

* Relatedness
    * two words can be related in any way
        * class <-> instance, hypernym, …
    * car, gasoline: related, not similar
    * car, vehicle: car is a hyponym of vehicle

## Named Entity Recognition (NER)
* identifies terms and phrases in language that refer to specific types of entities and relations in text

* entities = specific objects of interest such aspeople, places, organizations, dates, etc.
    * `Michael Dell is the CEO of Dell Computer Corporation and lives in Austin Texas.`
    * for domain-specific information: products, genes, laws, …

* relations = specific connections between entities such as relations concerning people (mother of, friend of, subordinate of), time (before, after), taxonomy (synonym, hypernym, example of, …), …
    * `Michael Dell is the CEO of Dell Computer Corporation and lives in Austin Texas.`
    * for domain-specific information: requires, known diseases, is applicable for, …

### Maximum Entropy Classifier

* MaxEnt classifier (aka multinomial logistic regression)
    * for a given input x estimate
    * where Z is a normalizing factor so that Σ𝑖𝑖𝑝𝑝𝑐𝑐𝑖𝑖𝑥𝑥)=1

* the "trick" of the MaxEntis the estimation of the weights
    * in a training set, observe the frequencies of fjfor x classified as ci
    * distribute the probability that x belongs to ciaccordingly over the weights
    * for features where we do not have observations (yet), distribute the remaining probability evenly(fair sharing)
        * i.e., we assign each feature the same importance (=information content or entropy) unless we have an observation that offers a better explanation
    * thus we maximize the overall entropy (hence the name)
    * basically an optimization problem with different solution strategies (heuristics) for the actual distribution

## Language Models & SyntacticText Analysis

### Levels of Understanding

* Syntax
    * includes all rules for creating
    * words from morphemes (= stem and affixes)
    * sentences from words
    * concerns the proper ordering of words and its effect on the statement’s meaning
    * `The dog bit the boy!= The boy bit the dog` = grammar

* Semantics
    * concerns the (literal) meaning of words, phrases, and sentences in a statement
    * `plant: a photosynthetic organism, a manufacturing facility, the act of sowing`

* Pragmatics
    * concerns the statement’s context and its effect on the statement’s interpretation
    * context: prior knowledge, environment (time, location, weather, …), speaker personality and intent, …
        * `Youhavea greenlight.`
    * speakerintent: irony
        * `Der Jaguar braucht 20 Liter, ein echtes Ökomobil.`

* Discourse
    * concerns a related series of statements in a speech, dialog, essay, … and its effect on the statements’ meanings
    * e.g., coreferenceresolution
        * `Der Jaguar braucht 20 Liter. Wenn man Spass haben will, auch mal mehr. Er ist ein echtes Ökomobil.`
    * e.g., overall intent: text summarization, sentiment analysis

### Linguistic Methods for Analyzing Syntax
* Tokenization
* Morphological Analysis
* Part of Speech Tagging
* Shallow Parsing
* Deep Syntactic Parsing

## Word Sentiments
sentiment `a view or opinion that is held or expressed.`

* How to map words to sentiments?
    * like, love, want, fantastic ->positive sentiment
    * dislike, unnecessary, defect -> negative sentiment

* Sentiment Lexicon
    * manually labeled
    * e.g., The General Inquirer (http://www.wjh.harvard.edu/~inquirer)
        * categories
        * Positiv(1915 words) and Negativ(2291 words)
        * Strong vs Weak, Active vs Passive, Overstated versus Understated
        * Pleasure, Pain, Virtue, Vice, Motivation, Cognitive Orientation, etc
    * free for research purposes

* researchers also try to create (or extend) lexicons automatically

## Bayes' Theorem
* mathematical law about conditional probabilities
* given two events Aand B, then the conditional probability P(B|A)relates to the probability that event B occurs after Ahas occurred
    * applies, e.g., when we are blindly drawing samples from a bag containing red and black balls without returning the balls
        * assume we start with 2 red and 2 black balls
            * then P(red) = 2/4 = P(black)for the first draw
        * if we draw a red ball first, then we know upfront for the 2nd draw
            * P(red|red) = 1/3and P(black|red) = 2/3

* probability that two conditionally-related events P(A) and P(B|A)occur one after another:P (A ∩B) = P(A) * P(B|A)
    * e.g., probability to first draw red and then red again:P (red ∩red) = P(red) * P(red|red) = 2/4 * 1/3 = 1/6

* P(A|B), P(B|A)may be calculated as
    * P(A|B) = P (A ∩B) / P(B)and P(B|A) = P(A ∩B) / P(A)(for P(B) ≠0 and P(A) ≠0)
    * in the drawing example, P(red|red) = 2/4 * 1/3 / 2/4 = 1/3

* from this we can derive Bayes' theorem
    * P(A|B) = P(B|A) P(A) / P(B)