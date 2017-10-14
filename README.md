# translit-scala
translit-scala is a transliteration library for Scala and Scala.js. It implements transliteration rules for Slavic languages. It supports converting texts from the Latin to the Cyrillic alphabet.

## Examples
```scala
translit.Ukrainian.latinToCyrillic("Kyiv")  // Київ
```

The transliteration to Cyrillic also restores soft signs (ь) and apostrophes ('):

```scala
translit.Ukrainian.latinToCyrillic("Mar'iana")  // Мар'яна
translit.Ukrainian.latinToCyrillic("p'iat'")    // п'ять
```

## Ukrainian
There have been several attempts to standardise rules. For example, *Український правопис* could be transliterated as follows:

* Ukrayins'kyy pravopys (BGN/PCGN 1965)
* Ukrains'kyi pravopys (National 1996)
* Ukrainskyi pravopys ([National 2010](http://zakon1.rada.gov.ua/laws/show/55-2010-%D0%BF))

Furthermore, there are language-specific transliterations, e.g. in French and German, that use the spelling conventions of the respective language.

Please refer to [Romanisation of Ukrainian](http://en.wikipedia.org/wiki/Romanization_of_Ukrainian) for more information.

*National 2010* is the most recent standard and the one implemented by translit-scala. As an extension, soft signs and apostrophes are restored. However, the standard requires to drop soft signs and apostrophes which makes the conversion irreversible. To comply with National 2010, you can set `apostrophes` to `false`.

### Credits
The rules and examples were adapted from the following libraries:

* [translit-english-ukrainian](https://github.com/MarkovSergii/translit-english-ukrainian)
* [translit-ua](https://github.com/dchaplinsky/translit-ua)

## Licence
translit-scala is licensed under the terms of the Apache v2.0 licence.

## Contributors
* Tim Nieradzik
