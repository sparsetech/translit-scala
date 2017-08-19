# translit-scala
translit-scala is a transliteration library for Scala and Scala.js. It implements transliteration rules for Slavic languages. It supports converting texts from the Latin to the Cyrillic alphabet.

## Examples
```scala
translit.Ukrainian.latinToCyrillic("Kyiv")  // Київ
```

The transliteration to Cyrillic does not restore soft signs (ь) or apostrophes ('):

```scala
translit.Ukrainian.latinToCyrillic("Mariana")  // Маряна (correct form: Мар'яна)
```

## Ukrainian
There have been several attempts to standardise rules. For example, *Український правопис* could be transliterated as follows:

* Ukrayins'kyy pravopys (BGN/PCGN 1965)
* Ukrains'kyi pravopys (National 1996)
* Ukrainskyi pravopys ([National 2010](http://zakon1.rada.gov.ua/laws/show/55-2010-%D0%BF))

Furthermore, there are language-specific transliterations, e.g. in French and German, that use the spelling conventions of the respective language.

Please refer to [Romanisation of Ukrainian](http://en.wikipedia.org/wiki/Romanization_of_Ukrainian) for more information.

*National 2010* is the most recent standard and the one implemented by translit-scala. It requires to drop soft signs and apostrophes which make the conversion irreversible.

### Credits
The rules and examples were adapted from the following libraries:

* [translit-english-ukrainian](https://github.com/MarkovSergii/translit-english-ukrainian)
* [translit-ua](https://github.com/dchaplinsky/translit-ua)

## Licence
translit-scala is licensed under the terms of the Apache v2.0 licence.

## Contributors
* Tim Nieradzik
