# <img src="http://sparse.tech/icons/translit.svg" width="50%">
[![Build Status](http://ci.sparse.tech/api/badges/sparsetech/translit-scala/status.svg)](http://ci.sparse.tech/sparsetech/translit-scala)
[![Maven Central](https://img.shields.io/maven-central/v/tech.sparse/translit-scala_2.12.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22tech.sparse%22%20AND%20a%3A%22translit-scala_2.12%22)

translit-scala is a transliteration library for Scala and Scala.js. It implements transliteration rules for Slavic languages. It supports converting texts from the Latin to the Cyrillic alphabet and vice-versa.

## Features
* Supported languages
	* Russian
	* Ukrainian
* Incremental transliteration
* Transliterations reversible with an accuracy of 99.99%
* Transliterations optimised for typing and reading
	* Only letters from the US keyboard are used
	* All common letters can be typed with a single keystroke
	* Convenience shortcuts are provided
* Cross-platform support (JVM, Scala.js)
* Zero dependencies

## Compatibility
| Back end   | Scala versions |
|:-----------|:---------------|
| JVM        | 2.11, 2.12     |
| JavaScript | 2.11, 2.12     |

## Dependencies
```scala
libraryDependencies += "tech.sparse" %%  "translit-scala" % "0.1.1"  // JVM
libraryDependencies += "tech.sparse" %%% "translit-scala" % "0.1.1"  // JavaScript
```

## Examples
```scala
translit.Ukrainian.latinToCyrillic("Kyyiv")  // Київ
translit.Russian.latinToCyrillic("pal`ma")   // пальма
```

## Ukrainian
There have been several attempts to standardise transliteration rules. For example, *Український правопис* could be transliterated as one of the following:

* Ukrayins'kyy pravopys (BGN/PCGN 1965)
* Ukrains'kyi pravopys (National 1996)
* Ukrainskyi pravopys ([National 2010](http://zakon1.rada.gov.ua/laws/show/55-2010-%D0%BF))
* Ukrayins\`kyj pravopys (*translit-scala*)

Furthermore, there are language-specific transliterations, e.g. in German and French, that use the spelling conventions of the respective language (*sch* in German instead of *sh* in English).

Please refer to [Romanisation of Ukrainian](http://en.wikipedia.org/wiki/Romanization_of_Ukrainian) for more details.

*National 2010* is the most recent standard which is mainly used for transliterating names and cities. A limitation is that it requires to drop soft signs and apostrophes. For better appearance, some letters can have multiple spellings depending on the position in the word. For example, я is transliterated as *ya* (prefix) or *ia* (infix). These restrictions make the conversion irreversible for many words.

Our transliteration was initially based on National 2010, but modified in the process to make the conversion reversible. Now it is most similar to BGN/PCGN. The rules are a compromise between compactness and simplicity.

### Differences from National 2010
#### и, й, я, є, ї, ю
We decompose letters in their Latin transliteration more consistently than National 2010. The letter и always gets transcribed as *y*:

* Volodymyr (Володимир)
* blyz\`ko (близько)

The Latin letter *y* forms the phonetic basis of four letters (iotated vowels) in the Ukrainian alphabet: я, є, ї, ю. They get transliterated accordingly:

* ya → я
* ye → є
* yi → ї
* yu → ю

Unlike National 2010, we always use the same transliteration regardless of the position in the word.

The accented counterpart of и is й and is represented by a separate letter, *j*.

*Example:* Zgurs\`kyj (Згурський)

#### Soft Signs and Apostrophes
The second change to National 2010 is that we retain soft signs (ь) and apostrophes ('):

* Ukrayins\`kyj (Український), malen\`kyj (маленький)
* m'yaso (м'ясо), matir'yu (матір'ю)

In National 2010, *g* gets mapped to *ґ* which is phonetically accurate, though the letter *ґ* is fairly uncommon in Ukrainian. Therefore, we represent *ґ* by the bi-gram *g'*.

#### Convenience mappings
Another modification was to provide the following mappings:

* c → ц
* h → х
* q → щ
* w → ш
* x → ж

Note that these mappings are phonetically inaccurate. However, using them still has a few advantages:

* Every letter of the Latin alphabet is covered
* When the user types *ch*, we can map the first letter to *ц*, then replace it by *ч*. Without this rule, the user would not get any visual feedback.
* Furthermore, the following mappings were chosen considering the similarity in shapes:
    * *w* has a similar shape to *ш*
    * *x* has a similar shape to *ж*
* Another advantage is the proximity on the English keyboard layout:
    * *q* and *w* are located next to each other; *ш* and *щ* characters are phonetically close
    * *z* and *x* are located next to each other; *з* and *ж* characters are phonetically close
* *h* is mapped to *х* since it is a common letter, *kh* is only needed in case *h* is ambiguous
	* An example is the word: схильність. The transliteration of *сх* corresponds to two separate letters *s* and *h*, which would map to *ш*. To prevent this, one can use the bi-gram *kh* instead to represent *х*. The full transliteration then looks as follows: *skhyl\`nist*

#### Escaping
You can insert a backslash (\\) if a replacement rule should not be applied, for example:

* stranstvy\e -> странствие
* pidtry\annya -> підтриання
* Bash\charshiyi -> Башчаршії

## Russian
The Russian rules are similar to the Ukrainian ones.

Some differences are:

* *i* corresponds to *и*, whereas *y* to *ы*
* Russian distinguishes between soft and hard signs. Apostrophes only appear in foreign names. The following mappings are used:
  * Soft sign: *`* for ь
  * Hard sign: *~* for ъ

### Mapping
| Latin | Cyrillic |
|-------|----------|
| a     | а        |
| b     | б        |
| c     | ц        |
| d     | д        |
| e     | е        |
| f     | ф        |
| g     | г        |
| h, kh | х        |
| i     | и        |
| j     | й        |
| k     | к        |
| l     | л        |
| m     | м        |
| n     | н        |
| o     | о        |
| p     | п        |
| q     | щ        |
| r     | р        |
| s     | с        |
| t     | т        |
| u     | у        |
| v     | в        |
| w     | ш        |
| x     | ж        |
| y     | ы        |
| z     | з        |
| \`    | ь        |
| ~     | ъ        |
| ch    | ч        |
| sh    | ш        |
| ya    | я        |
| ye    | э        |
| zh    | ж        |
| yo    | ё        |
| yu    | ю        |
| shch  | щ        |

### Examples
| Russian  | Transliterated |
|----------|----------------|
| Привет   | Privet         |
| Съел     | S~el           |
| Щётка    | Shchyotka      |
| Льдина   | L\`dina        |
| красивые | krasivy\e      |
| сходить  | skhodit\`      |

## Internals
The replacement patterns are applied sequentially by traversing the input character-by-character. The functions `latinToCyrillicIncremental` and `cyrillicToLatinIncremental` take the left context which is needed by some rules, for example to determine the correct case of soft/hard signs. The result of the functions indicates the number of characters to remove on the right as well as their string replacement.

```scala
def latinToCyrillicIncremental(
  latin: String, cyrillic: String, append: Char
): (Int, String)

def cyrillicToLatinIncremental(
  cyrillic: String, letter: Char
): (Int, String)
```

## Performance
The test suite evaluates whether transliterations are reversible. The accuracy is calculated on words extracted from Wikipedia article dumps for all supported languages. Words are transliterated to Latin and then back to Cyrillic. A word counts as correct if the result of the reversed transliteration matches the original.

| Language  | Total     | Correct   | Accuracy |
|-----------|-----------|-----------|----------|
| Ukrainian | 1,811,772 | 1,811,661 | 99.99%   |
| Russian   | 1,529,184 | 1,529,043 | 99.99%   |

## Credits
The rules and examples were adapted from the following libraries and websites:

* [translit-english-ukrainian](https://github.com/MarkovSergii/translit-english-ukrainian)
* [translit-ua](https://github.com/dchaplinsky/translit-ua)
* [translit.net](http://translit.net/)

## Licence
translit-scala is licensed under the terms of the Apache v2.0 licence.

## Contributors
* Tim Nieradzik
* Darkhan Kubigenov
