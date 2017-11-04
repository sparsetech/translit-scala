# translit-scala
translit-scala is a transliteration library for Scala and Scala.js. It implements transliteration rules for Slavic languages. It supports converting texts from the Latin to the Cyrillic alphabet.

## Examples
```scala
translit.Ukrainian.latinToCyrillic("Kyyiv")  // Київ
```

The transliteration to Cyrillic also restores soft signs (ь) and apostrophes ('):

```scala
translit.Ukrainian.latinToCyrillic("Mar'yana")  // Мар'яна
translit.Ukrainian.latinToCyrillic("p'yat'")    // п'ять
```

## Ukrainian
There have been several attempts to standardise transliteration rules. For example, *Український правопис* could be transliterated as one of the following:

* Ukrayins'kyy pravopys (BGN/PCGN 1965)
* Ukrains'kyi pravopys (National 1996)
* Ukrainskyi pravopys ([National 2010](http://zakon1.rada.gov.ua/laws/show/55-2010-%D0%BF))
* Ukrayins'kyy pravopys (**translit-scala**)

Furthermore, there are language-specific transliterations, e.g. in German and French, that use the spelling conventions of the respective language (*sch* in German instead of *sh* in English).

Please refer to [Romanisation of Ukrainian](http://en.wikipedia.org/wiki/Romanization_of_Ukrainian) for more details.

*National 2010* is the most recent standard which is mainly used for transliterating names and cities. A limitation is that it requires to drop soft signs and apostrophes. For better appearance, some letters can have multiple spellings depending on the position in the word. For example, я is transliterated as *ya* (prefix) or *ia* (infix). These restrictions make the conversion irreversible for many words.

Our transliteration was initially based on National 2010, but modified in the process to make the conversion reversible. Now it is most similar to BGN/PCGN. The rules are a compromise between compactness and simplicity.

### Differences from National 2010
#### и, й, я, є, ї, ю
We decompose letters in their Latin transliteration more consistently than National 2010. The letter и always gets transcribed as *y*:

* Volodymyr (Володимир)
* blyz'ko (близько)

The Latin letter *y* is also the phonetic basis of four letters in the Slavic alphabet: я, є, ї, ю. They get transliterated accordingly:

* ya → я
* ye → є
* yi → ї
* yu → ю

Unlike National 2010, we always use the same transliteration regardless of the position in the word.

The accented counterpart of и is й. It is only used in conjunction with vowels. This lets us define the following rules without mapping й onto a separate letter:

* ay → ай
* ey → ей
* iy → ій
* yy → ий
* yo → йо

*Examples:*

* kofeyin (кофеїн)
* Zghurskyy (Згурський)

#### Soft Signs and Apostrophes
The second change to National 2010 is that we try to restore soft signs and apostrophes:

* Ukrayins'kyy (Український)
* malen'kyy (маленький)

This feature is experimental and can be disabled by setting `apostrophes` to `false`.

### Credits
The rules and examples were adapted from the following libraries:

* [translit-english-ukrainian](https://github.com/MarkovSergii/translit-english-ukrainian)
* [translit-ua](https://github.com/dchaplinsky/translit-ua)

## Licence
translit-scala is licensed under the terms of the Apache v2.0 licence.

## Contributors
* Tim Nieradzik
