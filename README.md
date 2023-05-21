# recfm-maven-plugin

## Table of Contents

[1) Introduction](#1)<br/>
[2) Preview](#2)<br/>
[3) Plugin parameters details](#3)<br/>
[4) Configuration YAML details](#4)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;[4.1) Package level](#41)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.1.1) Package default for alphanumeric](#411)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.1.2) Package default for numeric](#412)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.1.3) Package default for filler](#413)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.1.4) Package default for custom](#414)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;[4.2) Class level](#42)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;[4.3) Field level](#43)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.1) `Abc` Alphanumeric](#431)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.2) `Num` Numeric](#432)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.3) `Cus` Custom](#433)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.4) `Dom` Domain](#434)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.5) `Fil` Filler](#435)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.6) `Val` Constant](#436)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.7) `Grp` Group](#437)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.8) `Occ` Occurs](#438)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.9) `GRP` Group (with interface)](#439)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.10) `OCC` Occurs (with interface)](#43a)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.3.11) `Emb` Embedded group (with interface)](#43b)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;[4.4) Interface level](#44)<br/>
5) Special methods [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#5) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#5)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;5.1) `decode` [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#51) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#51)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;5.2) `encode` [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#52) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#52)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;5.3) `toString` [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#53) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#53)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;5.4) Validation method [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#54) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#54)<br/>

---

## <a name="1">1. Introduction</a>

[//]: # (può capitare che una serie di dati venga salvata su un file.)
It may happen that a series of data is saved to a file.

[//]: # (uno dei modi di serializzare il dato è convertirlo in una stringa posizionale.)
One way to serialize data is to convert it to a positional string.

[//]: # (ogni campo del dato viene trasformato in una stringa di lunghezza massima prefissata, queste stringhe vengono concatenate per formare una singola riga del file.)
Each data field is transformed into a string of pre-set maximum length, these strings are concatenated to form a single line of the file.

[//]: # (Per recuperare ogni campo del dato da una riga del file è necessario eseguire l'operazione inversa.)
To retrieve each data field from a line of the file it is necessary to perform the reverse operation.

[//]: # (non esiste una ricetta definitiva per questo tipo di deserializzazione della serializzazione)
There is no ultimate recipe for this type of serialization deserialization.

[//]: # (se ci sono pochi campi, il codice di serializzazione può essere scritto manualmente, ma al crescere dei campi questa soluzione è impraticabile.)
* If there are few fields, the serialization code can be written manually, but as the fields grow this solution is impractical.

[//]: # (Un'altra possibilità è aggiungere delle annotazioni sui campi del dato che forniscono le informazioni supplementari per permettere la serializzazione di ogni campo. un gruppo di classi dedicate, utilizza i valori dei campi e della relativa annotazione per serializzare deserializzare dei campi)
* Another possibility is to add annotations on the data fields which provide additional information to allow the serialization of each field. A set of dedicated classes, uses the values of fields and their annotation to serialize deserialize fields.

[//]: # (la soluzione proposta con questo plugin è quella di memorizzare le informazioni sul dato, le regole di serializzazione, e di validazione in un file di configurazione. il file viene dato in pasto al plugin, che ne verifica la consistenza, e genera la classe corrispondente ai dati con i metodi setter, getter, serializzazione, deserializzazione e validazione)
The solution proposed with this plugin is to store information about the data, serialization and validation rules in a configuration file.
The file is fed to the plugin, which checks its consistency, and generates the class corresponding to the data with the setter, getter, serialization, deserialization and validation methods.

[//]: # ()

Numeric fields are handled as strings that accept only numeric characters. For these fields it is also possible to request the generation of getters and setters with primitive numeric types.

## <a name="2">2. Preview</a>

Configuration plugin example:

~~~xml
<plugin>
    <groupId>io.github.epi155</groupId>
    <artifactId>recfm-maven-plugin</artifactId>
    <version>0.7.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <settings>
                    <setting>recfm-foo.yaml</setting>
                </settings>
            </configuration>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>io.github.epi155</groupId>
            <artifactId>recfm-java-addon</artifactId>
            <version>0.7.0</version>
        </dependency>
    </dependencies>
</plugin>
~~~

Configuration file example:

~~~
packageName: org.example.sys.file
classes:
  - name: FooBody
    len: 543
    fields:
      - !Num { name: ibrKey  , at:   1, len:  11 }
      - !Num { name: ibrPrg  , at:  12, len:   6 }
      - !Num { name: recPrg  , at:  18, len:   9 }
      - !Abc { name: funcData, at:  27, len: 500 }
      - !Abc { name: reseData, at: 527, len:  17 }
~~~

The plungin uses goal name `generate`, default phase `generate-sources`, required dependency resolution scope `compile`, required dependency collection scope `compile`.
Generated code examples [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#2-xmpl) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#2-xmpl)

Generated code may require dependencies

~~~xml
        <dependency>
            <groupId>io.github.epi155</groupId>
            <artifactId>recfm-java-lib</artifactId>
            <version>0.7.0</version>
        </dependency>
~~~

The plugin is modular. 

* `recfm-maven-plugin` provides input parameters and starts code generation, 
* `recfm-addon-api` provides configuration yaml structure, consistency checks of user-supplied yaml and code generation API signature.
* `recfm-***-addon` provides the implementation of the code generation API, 
* `recfm-***-lib` provides the generic classes used by the generated code


## <a name="3">3. Plugin parameters details</a>

Parameters

`generateDirectory` or property `maven.recfm.generateDirectory`
: Indicates the base directory from which to generate packages, default value is `${project.build.directory}/generated-sources/recfm`,
ie **`target/generated-sources/recfm`**

`settingsDirectory` or property `maven.recfm.settingsDirectory`
: Indicates the base directory that contains the configuration files, default value
is `${project.build.resources[0].directory}`, ie **`src/main/resources`**

**`settings`**
: List of configuration files (required).

`doc` or property `maven.recfm.doc`
: Indicates whether to generate the documentation on setters and getters, default is **`true`**.

`align` or property `maven.recfm.align`
: Indicates the minimum alignment of numeric fields when numeric representation is required. The Default value is **4**,
ie setters and getters are adapted to `int` or `long` depending on the length of the field. Using 2 also uses `short`,
and using 1 also uses `byte`.

`enforceGetter` or property `maven.recfm.enforceGetter`
: indicates whether to check the value before returning the value. Default value is **`true`**.

`enforceSetter` or property `maven.recfm.enforceSetter`
: indicates whether to check the supplied value before setting it in the record. Default value is **`true`**.

property `maven.recfm.addCompileSourceRoot`
: If set to **true** (default), adds target directory as a compile source root of this Maven project.

property `maven.recfm.addTestCompileSourceRoot`
: If set to true, adds target directory as a test compile source root of this Maven project. Default value is **false**.

`codeProviderClassName`
: class implementing addon-api, not needed if code is generated in only one language.
If you need to generate code in different languages, you need to use an execution for each language and specify the codeProviderClassName

~~~xml
<plugin>
    <groupId>io.github.epi155</groupId>
    <artifactId>recfm-maven-plugin</artifactId>
    <version>0.7.0</version>
    <executions>
        <execution>
            <id>java</id>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <generateDirectory>target/generated-sources/recfm/java</generateDirectory>
                <settings>
                    <setting>java-conf.yaml</setting>
                </settings>
                <codeProviderClassName>io.github.epi155.recfm.java.CodeGenerator</codeProviderClassName>
            </configuration>
        </execution>
        <execution>
            <id>scala</id>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <generateDirectory>target/generated-sources/recfm/scala</generateDirectory>
                <settings>
                    <setting>scala-conf.yaml</setting>
                </settings>
                <codeProviderClassName>io.github.epi155.recfm.scala.CodeGenerator</codeProviderClassName>
            </configuration>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>io.github.epi155</groupId>
            <artifactId>recfm-java-addon</artifactId>
            <version>0.7.0</version>
        </dependency>
        <dependency>
            <groupId>io.github.epi155</groupId>
            <artifactId>recfm-scala-addon</artifactId>
            <version>0.7.0</version>
        </dependency>
    </dependencies>
</plugin>
~~~

## <a name="4">4. Configuration YAML details</a>

In general, we can have multiple configuration files. Each configuration file can define multiple classes within a
package.

### <a name="41">4.1. Package level</a>

The first thing to define is the package name

~~~yml
packageName: org.example.sys.file
~~~

Then we can assign some predefined behaviors, these behaviors can also be defined at the field level, in this case they
are defined at the package level

~~~yml
defaults:
  abc:
    check: Ascii
    onOverflow: Trunc
    onUnderflow: Pad
    normalize: None
  num:
    onOverflow: Trunc
    onUnderflow: Pad
    normalize: None
  fil:
    check: None
    fill: 0
  cus:
    align: LFT
    check: Ascii
    init: ' '
    pad: ' '
    onOverflow: Trunc
    onUnderflow: Pad
    normalize: None
~~~

#### <a name="411">4.1.1. Package default for alphanumeric</a>

`defaults.abc.check` indicates which checks to perform on alphanumeric fields, the default value is **Ascii**.

`defaults.abc.onOverflow` indicates how to behave on setter if the length of the supplied string is
  greater than that expected, default value is **Trunc** (ie characters exceeding to the right are ignored).

`defaults.abc.onUnderflow` indicates how to behave on setter if the length of the supplied string is
  less than the expected one, default value is **Pad** (ie the value is completed with spaces).

`defaults.abc.normalize` indicates how to behave on the getter, the padding spaces on the right can be removed,
the default value is **None** (i.e. no spaces to the right are removed)

#### <a name="412">4.1.2. Package default for numeric</a>

`defaults.num.onOverflow` indicates how to behave on setter if the length of the supplied string is
  greater than that expected, default value is **Trunc** (ie characters exceeding to the left are ignored).

`defaults.num.onUnderflow` indicates how to behave on setter if the length of the supplied string is
  less than the expected one, default value is **Pad** (ie the value is completed with zeroes).

`defaults.num.normalize`

#### <a name="413">4.1.3. Package default for filler</a>

`defaults.fil.check` indicates which checks to perform on Filler fields, the default value is **None**.

`defaults.fil.fill` is the fill character to use for fields of type *Filler*, the default value is **0** (ie **\u0000**)

#### <a name="414">4.1.4. Package default for custom</a>

`defaults.cus.align` indicates which the align rule on *Custom* fields, the default value is **LFT** (ie **left**).

`defaults.cus.check` indicates which checks to perform on *Custom* fields, the default value is **Ascii**.

`defaults.cus.init` is the initializer character to use for fields of type *Custom*, the default value is **' '** (ie **SPACE**)

`defaults.cus.pad` is the pad character to use for fields of type *Custom*, the default value is **' '** (ie **SPACE**)

`defaults.cus.onOverflow`

`defaults.cus.onUnderflow`

`defaults.cus.normalize`



After that we can define the single classes

~~~yml
classes:
  - {...}
~~~

### <a name="42">4.2. Class level</a>

A string is associated with each class. At the class level the following parameters can be defined:

~~~yml
classes:
  - name: FooBody
    length: 543
    onOverflow: Trunc
    onUnderflow: Pad
    fields:
      - {...}
~~~

* `classes[].name` is the name of the class.
* `classes[].length` is the length of the string (`length` can be abbreviated to `len`)
* `classes[].onOverflow` indicates how to behave in the deserialization phase if the length of the supplied string is
  greater than that expected, default value is **Trunc** (ie extra characters are ignored), the alternate value is **
  Error** that throws a *RecordOverflowException*.
* `classes[].onUnderflow` indicates how to behave in the deserialization phase if the length of the supplied string is
  less than the expected one, default value is **Pad** (ie ..), the alternate value is **Error** that throws a *
  RecordUnderflowException*.

### <a name="43">4.3. Field level</a>

It is possible to define various types of fields, each of which has specific attributes. For this reason a specific json
tag is used for each type, Let's see them in detail one by one.

The entire area of the class or of a group of it must be associated with a field. If a part of the area is not needed it
should be defined as a filler.

#### <a name="431">4.3.1. Alphanumeric </a>

Tag for alphanumeric field is `Abc`, the possible attributes are:

| attribute                | alt |  type   | note                               |
|--------------------------|-----|:-------:|------------------------------------|
| [offset](#fld.offset)    | at  |   int   | **required**                       |
| [length](#fld.length)    | len |   int   | **required**                       |
| [name](#fld.name)        |     | String  | **required**                       |
| [redefines](#fld.redef)  | red | boolean | default `false`                    |
| [audit](#fld.audit)      |     | boolean | default `false`                    |
| [onOverflow](#fld.ovfl)  | ovf |  [^1]   | default `Trunc`                    |
| [onUnderflow](#fld.unfl) | unf |  [^2]   | default `Pad`                      |
| [padChar](#fld.pchr)     | pad |  char   | default value `' '`                |
| [check](#fld.chk)        | chk |  [^3]   | default value `defaults.abc.check` |

[^1]: Overflow domain: Trunc, Error
[^2]: Underflow domain: Pad, Error
[^3]: CheckA domain: None, Ascii, Latin1, Valid

Some attributes also have a shortened form. The meaning of some attributes is immediate.
The <a name='fld.offset'>offset</a> attribute indicates the starting position of the field (starting from 1).
The <a name='fld.length'>length</a> attribute indicates the length of the field.
The <a name='fld.name'>name</a> attribute indicates the name of the field.

~~~yml
classes:
  - name: Foo
    length: 75
    fields:
      - !Abc { name: huey , at:  1, len: 25 }
      - !Abc { name: dewey, at: 26, len: 25 }
      - !Abc { name: louie, at: 51, len: 25 }
~~~

Generated code example [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#431-xmpl) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#431-xmpl)

The <a name='fld.redef'>redefines</a> attribute indicates that the field is a redefinition of an area, this field will
not be considered in the overlay checks

~~~yml
  - name: Foo
    length: 10
    fields:
      - !Abc { name: isoDate , at: 1, len: 10 }   # yyyy-MM-dd
      - !Num { name: year    , at: 1, len: 4, red: true }
      - !Num { name: month   , at: 6, len: 2, red: true }
      - !Num { name: day     , at: 9, len: 2, red: true }
~~~

To introduce the <a name='fld.audit'>audit</a> attribute see section [5.4](#54).

<a name='fld.ovfl'>onOverflow</a> indicates what to do if you try to set a value whose length is greater than the
defined one. It is possible to set `Trunc` and `Error`, in the first two cases the value is truncated, respectively to
the left or to the right, in the last case an exception is thrown.

<a name='fld.unfl'>onUnderflow</a> indicates what to do if you try to set a value whose length is less than the defined
one. It is possible to set `Pad` and `Error`, in the first two cases the value is padded respectively to the left or to
the right, in the last case an exception is thrown.

<a name='fld.pchr'>padchar</a> indicates the character to use for padding, in case of underflow.

<a name='fld.chk'>check</a> indicates which checks to perform in the *validate* or *audit* phase. The following values
are available:

`None`
: no check will be performed

`Ascii`
: only ascii characters are accepted, control characters are not accepted (95 characters only)

`Latin1`
: only latin1 (ISO-8859-1) characters are accepted, control characters are not accepted (190 characters only)

`Valid`
: characters that pass the `Character.isDefined(c)` test are accepted

#### <a name="432">4.3.2. Numeric </a>

Tag for numeric field is `Num`, many attributes have the same meaning as in the alphanumeric case, the padding character
is necessarily 0, the control is necessarily that the characters are numeric, the possible attributes are:

| attribute                 | alt |  type   | note                               |
|---------------------------|-----|:-------:|------------------------------------|
| [offset](#fld.offset)     | at  |   int   | **required**                       |
| [length](#fld.length)     | len |   int   | **required**                       |
| [name](#fld.name)         |     | String  | **required**                       |
| [redefines](#fld.redef)   | red | boolean | default `false`                    |
| [audit](#fld.audit)       |     | boolean | default `false`                    |
| [onOverflow](#fld.ovfl)   | ovf |  [^1]   | default `defaults.num.onOverflow`  |
| [onUnderflow](#fld.unfl)  | unf |  [^2]   | default `defaults.num.onUnderflow` |
| [normalize](#fld.norm)    |     |  [^5]   | default `defaults.num.normalize`   |
| [numericAccess](#fld.num) | num | boolean | default value `false`              |

[^5]: NormalizeN domain: None, Trim

<a name='fld.num'>numericAccess</a> indicates whether to generate the numeric setters and getters for the field, in
addition to the alphanumeric ones. Numeric getters are prefixed with the return type.

~~~yml
  - name: Foo
    length: 10
    fields:
      - !Num { name: year , at: 1, len: 4, num: true }
      - !Fil { at: 5, len: 1 }
      - !Num { name: month, at: 6, len: 2, num: true }
      - !Fil { at: 8, len: 1 }
      - !Num { name: date , at: 9, len: 2, num: true }
~~~

Generated code example [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#432-xmpl) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#432-xmpl)

#### <a name="433">4.3.3. Custom </a>
Tag for custom field is `Cus`, a custom field is an extension of an alphanumeric field, with some additional parameters

| attribute                | alt |  type   | note                               |
|--------------------------|-----|:-------:|------------------------------------|
| [offset](#fld.offset)    | at  |   int   | **required**                       |
| [length](#fld.length)    | len |   int   | **required**                       |
| [name](#fld.name)        |     | String  | **required**                       |
| [redefines](#fld.redef)  | red | boolean | default `false`                    |
| [audit](#fld.audit)      |     | boolean | default `false`                    |
| [onOverflow](#fld.ovfl)  | ovf |  [^1]   | default `defaults.cus.onOverflow`  |
| [onUnderflow](#fld.unfl) | unf |  [^2]   | default `defaults.cus.onUnderflow` |
| [normalize](#fld.norm)   |     |  [^3]   | default `defaults.cus.normalize`   |
| [padChar](#fld.pchr)     | pad |  char   | default `defaults.cus.pad`         |
| [initChar](#fld.ichr)    | ini |  char   | default `defaults.cus.init`        |
| [check](#fld.ichk)       | chk |  [^6]   | default `defaults.cus.check`       |
| [regex](#fld.regx)       |     | String  | default *null*                     |
| [align](#fld.ialign)     |     |  [^7]   | default `defaults.cus.align`       |

[^6]: CheckC domain: None, Ascii, Latin1, Valid, Digit, DigitOrBlank
[^7]: AlignC domain: LFT, RGT

<a name='fld.ichr'>initChar</a> indicates the character to use to initialize the field when the empty constructor is
used.

<a name='fld.ichk'>check</a> indicates which checks to perform in the *validate* or *audit* phase. The following values
are available:

`None` .. `Valid`
: see [alphanumeric](#fld.chk) case

`Digit`
: only numeric characters from 0 to 9 are accepted

`DigitOrBlank`
: digit or all space characters is accepted

<a name='fld.regx'>regex</a> if present indicates that the value must satisfy the regular expression, this check
overrides the [check](#fld.ichk) one. Regex control, on setter, is performed after any pad / truncate normalizations

<a name='fld.ialign'>align</a> indicates the direction to align the field in case the supplied length is different from
the available one.

#### <a name="434">4.3.4. Domain </a>

Tag for domain field is `Dom`, a domain field can only take a limited number of values, the possible attributes are:

| attribute               | alt |   type   | note            |
|-------------------------|-----|:--------:|-----------------|
| [offset](#fld.offset)   | at  |   int    | **required**    |
| [length](#fld.length)   | len |   int    | **required**    |
| [name](#fld.name)       |     |  String  | **required**    |
| [redefines](#fld.redef) | red | boolean  | default `false` |
| [audit](#fld.audit)     |     | boolean  | default `false` |
| [items](#fld.items)     |     | String[] | **required**    |

<a name='fld.items'>items</a> indicates the list of possible values that the field can assume. All values supplied must have the expected length for the field. The first value supplied will be used to initialize the field.

~~~yml
  - name: Foo
    length: 20
    fields:
      - !Abc { name: date    , at:  1, len: 10 }
      - !Num { name: amount  , at: 11, len:  7, num: true }
      - !Dom { name: currency, at: 18, len:  3, items: [ EUR, USD, CHF, GBP, JPY ] }
~~~



#### <a name="435">4.3.5. Filler </a>

Tag for filler field is `Fil`, a filler is an area we are not interested in, neither getters nor setters are generated
for it, the possible attributes are:

| attribute             | alt | type | note                               |
|-----------------------|-----|:----:|------------------------------------|
| [offset](#fld.offset) | at  | int  | **required**                       |
| [length](#fld.length) | len | int  | **required**                       |
| [fillChar](#fld.fill) | chr | char | default value `defaults.fil.fill`  |
| [check](#fld.chk)     | chk | [^4] | default value `defaults.fil.check` |

<a name='fld.fill'>fillChar</a> indicates the character to use to initialize the area

#### <a name="436">4.3.6. Constant </a>

Tag for constant field is `Val`, even for a constant field the setters and getters are not generated, the controls
verify that the present value coincides with the set one, the possible attributes are:

| attribute             | alt |  type   | note            |
|-----------------------|-----|:-------:|-----------------|
| [offset](#fld.offset) | at  |   int   | **required**    |
| [length](#fld.length) | len |   int   | **required**    |
| [value](#fld.val)     | val | String  | **required**    |
| [audit](#fld.audit)   |     | boolean | default `false` |

<a name='fld.val'>value</a> indicates the value with which to initialize the area

#### <a name="437">4.3.7. Group </a>

Tag for group field is `Grp`, a group allows you to group multiple fields in order to structure the area, the possible
attributes are:

| attribute               | alt |  type   | note                      |
|-------------------------|-----|:-------:|---------------------------|
| [offset](#grp.offset)   | at  |   int   | **required**              |
| [length](#grp.length)   | len |   int   | **required**              |
| [name](#grp.name)       |     | String  | **required**              |
| [redefines](#grp.redef) | red | boolean | default `false`           |
| [fields](#grp.flds)     |     |  array  | **required** child fields |

The <a name='grp.offset'>offset</a> attribute indicates the starting position of the group (starting from 1).
The <a name='grp.length'>length</a> attribute indicates the length of the group.
The <a name='grp.name'>name</a> attribute indicates the name of the group.
The <a name='grp.redef'>redefines</a> attribute indicates that the group is a redefinition of an area, this group will
not be considered in the overlay checks
The <a name='grp.flds'>fields</a> attribute indicates a definition list of fields

Group definition example (mixed yaml/json):

~~~yml
  - name: BarReq
    len: 19324
    fields:
      - !Grp { name: transactionArea       , at:  1, len: 12,
               fields: [
                 !Abc { name: cdTransazione, at:  1, len:  9 },
                 !Num { name: esitoAgg     , at: 10, len:  1 },
                 !Num { name: esitoCompl   , at: 11, len:  1 },
                 !Val { val: "\n"          , at: 12, len:  1 }
               ] }
      - ...
~~~

or (yaml style):

~~~yml
  - name: BarReq
    len: 19324
    fields:
      - !Grp 
        name: transactionArea 
        at:  1 
        len: 12
        fields:
          - !Abc { name: cdTransazione, at:  1, len:  9 }
          - !Num { name: esitoAgg     , at: 10, len:  1 }
          - !Num { name: esitoCompl   , at: 11, len:  1 }
          - !Val { val: "\n"          , at: 12, len:  1 }
      - ...
~~~

Group usage example: [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#437-xmpl) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#437-xmpl)

In general two fields can not have the same name (only one of them would be referenceable), but a group is something other than a field. In the Java implementation there are no referencing problems if a field and a group use the same name. But in the Scala implementation this is not possible.

A definition like this generates the java class successfully, but fails to generate the scala class.
~~~yaml
  - name: FooDate
    length: 10
    fields:
      - !Abc { name: date   , at: 1, len: 10 }
      - !Grp { name: date   , at: 1, len: 10, red: true, fields: [
          !Num { name: year , at: 1, len:  4, num: true },
          !Fil {              at: 5, len:  1 },
          !Num { name: month, at: 6, len:  2, num: true },
          !Fil {              at: 8, len:  1 },
          !Num { name: day  , at: 9, len:  2, num: true }
      ]}
~~~


#### <a name="438">4.3.8. Occurs </a>

Tag for occurs field is `Occ`, an occurs is basically a repeated group, it is defined with the group data of the first
occurrence and the number of occurrences, the possible attributes are:

| attribute               | alt |  type   | note                      |
|-------------------------|-----|:-------:|---------------------------|
| [offset](#occ.offset)   | at  |   int   | **required**              |
| [length](#occ.length)   | len |   int   | **required**              |
| [name](#occ.name)       |     | String  | **required**              |
| [redefines](#occ.redef) | red | boolean | default `false`           |
| [fields](#occ.flds)     |     |  array  | **required** child fields |
| [times](#occ.times)     | x   |   int   | **required** occurrences  |

The <a name='occ.offset'>offset</a> attribute indicates the starting position of the first group (starting from 1).
The <a name='occ.length'>length</a> attribute indicates the length of a single group.
The <a name='occ.name'>name</a> attribute indicates the name of the group.
The <a name='occ.redef'>redefines</a> attribute indicates that the group is a redefinition of an area, this group will
not be considered in the overlay checks
The <a name='occ.flds'>fields</a> attribute indicates a definition list of fields, the offsets of the fields are those
relative to the first group
The <a name='occ.times'>times</a> attribute indicates the number of times the group is repeated

Occurs definition example:

~~~yml
  - name: FooResp
    len: ...
    fields:
      - ...
      - !Occ { name: errItem                  , at: 92, len: 20, x: 25,
               fields: [
                 !Abc { name: applicationId   , at: 92, len: 2 },
                 !Abc { name: errorCodeSource , at: 94, len: 5 },
                 !Abc { name: errorCodeTarget , at: 99, len: 4 },
                 !Abc { name: aliasId         , at: 103, len: 8 },
                 !Fil { at: 111, len: 1 }
               ] }
      - ...
~~~

Occurs usage example: [<img src="java-24.png">](https://github.com/epi155/recfm-java/blob/0.7/recfm-java-addon/README.md#438-xmpl) [<img src="scala-24.png">](https://github.com/epi155/recfm-scala/blob/0.6/recfm-scala-addon/README.md#438-xmpl)

#### <a name="439">4.3.9. Group with interface </a>

The `GRP` tag allows you to define a group of fields by referencing an [interface](#44), the possible attributes are:

| attribute               | alt |  type   | note                             |
|-------------------------|-----|:-------:|----------------------------------|
| [offset](#GRP.offset)   | at  |   int   | **required**                     |
| [length](#GRP.length)   | len |   int   | **required**                     |
| [name](#GRP.name)       |     | String  | **required**                     |
| [redefines](#GRP.redef) | red | boolean | default `false`                  |
| [typeDef](#GRP.defs)    | as  | TypDef  | **required** interface reference |

The <a name='GRP.offset'>offset</a> attribute indicates the starting position of the group (starting from 1).
The <a name='GRP.length'>length</a> attribute indicates the length of the group.
The <a name='GRP.name'>name</a> attribute indicates the name of the group.
The <a name='GRP.redef'>redefines</a> attribute indicates that the group is a redefinition of an area, this group will
not be considered in the overlay checks
The <a name='GRP.defs'>typeDef</a> attribute indicates the interface to be used as a model to define the fields of the group

Group (with interface) definition example:

~~~yml
classes:
  - name: B280v2xReq
    length: 19324
    fields:
      - !GRP { name: transactionArea, at:   1, len:  12, as: *TransactionArea }
      - ...
~~~

#### <a name="43a">4.3.10. Occurs with interface </a>

The `OCC` tag allows you to define an occurs group of fields by referencing an [interface](#44), the possible attributes are:

| attribute               | alt |  type   | note                             |
|-------------------------|-----|:-------:|----------------------------------|
| [offset](#OCC.offset)   | at  |   int   | **required**                     |
| [length](#OCC.length)   | len |   int   | **required**                     |
| [name](#OCC.name)       |     | String  | **required**                     |
| [redefines](#OCC.redef) | red | boolean | default `false`                  |
| [typeDef](#OCC.defs)    | as  | TypDef  | **required** interface reference |
| [times](#OCC.times)     | x   |   int   | **required** occurrences         |

The <a name='OCC.offset'>offset</a> attribute indicates the starting position of the first group (starting from 1).
The <a name='OCC.length'>length</a> attribute indicates the length of a single group.
The <a name='OCC.name'>name</a> attribute indicates the name of the group.
The <a name='OCC.redef'>redefines</a> attribute indicates that the group is a redefinition of an area, this group will
not be considered in the overlay checks
The <a name='OCC.defs'>typeDef</a> attribute indicates the interface to be used as a model to define the fields of the group
The <a name='OCC.times'>times</a> attribute indicates the number of times the group is repeated

Occurs (with interface) definition example:

~~~yml
classes:
  - name: B320v2xRes
    length: 19324
    fields:
      - !GRP { name: transactionArea, at:   1, len:  12, as: *TransactionArea }
      - !GRP { name: messageArea    , at:  13, len: 100, as: *MessageArea }
      - !GRP { name: segmentArea    , at: 113, len:  11, as: *SegmentArea }
      - !Grp { name: rs1            , at: 124, len: 253, fields: [ ... ] }
      - !OCC { name: rs             , at: 377, len: 146, x: 129, as: *B320v2xItem }
      - !Fil {                      at: 19211, len: 114 }
~~~

#### <a name="43b">4.3.11. Embedded group with interface </a>

The `Emb` tag allows you to insert the fields defined in an [interface](#44) into the current structure, the possible attributes are:

| attribute               | alt |  type   | note                             |
|-------------------------|-----|:-------:|----------------------------------|
| [source](#emb.defs)     | src | TypDef  | **required** interface reference |
| [offset](#emb.offset)   | at  |   int   | **required**                     |
| [length](#emb.length)   | len |   int   | **required**                     |

The <a name='emb.defs'>typeDef</a> attribute indicates the interface to be used as a model to insert the fields
The <a name='emb.offset'>offset</a> attribute indicates the starting position of the first group (starting from 1).
The <a name='emb.length'>length</a> attribute indicates the length of a single group.

Embedded group definition example:

~~~yml
classes:
  - name: B280v2xReq
    length: 19324
    fields:
      - !Emb { src: *TransactionArea, at:   1, len:  12 }
      - ...
~~~


### <a name="44">4.4. Interface level</a>

Sometimes there may be structures that have a common partial definition.
In these cases it is useful to be able to manage these areas in the same way, as if they were the same sub-structure.
This can be done by defining an interface that defines the common sub-structure

~~~yml
interfaces:
  - &TransactionArea
    name: ITransactionArea
    length: 12
    fields:
      - !Abc { name: transId   , at:  1, len: 9 }
      - !Num { name: esitoAgg  , at: 10, len: 1 }
      - !Num { name: esitoCompl, at: 11, len: 1 }
      - !Val { val: "\n"       , at: 12, len: 1 }
~~~

and then we can define the common substructure in the class by referencing the interface.

~~~yml
classes:
  - name: B280v2xReq
    length: 19324
    fields:
      - !GRP { name: transactionArea, at:   1, len:  12, as: *TransactionArea }
      - ...
~~~

For the definition of the interface fields the same rules apply as for the classes.
The only exception is the offset of the first field defined in the interface, the natural value would be 1, but any value can be used, when the interface is applied to the class, the offset is corrected to fit the structure of the class
