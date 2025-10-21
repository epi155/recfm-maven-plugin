## SequenceNode

The data types were defined using MappingNode Construct, i.e. by providing key-value pairs, e.g.

~~~yaml
!Abc { name: foo, len: 10 }
~~~

Data types can now also be defined using SequenceNode Construct, i.e. by providing a list of values, e.g.

~~~yaml
!Abc [ foo, 10 ]
~~~

values are interpreted based on cardinality and type.

Each data type has an incremental constructor with all parameters recognized based on cardinality. In addition to this constructor, there may be reduced constructors that identify parameters based on cardinality and position.

Let's see in detail for each given type the incremental constructor and the reduced ones.

### `Abc`

Incremental constructor

~~~yaml
!Abc [ String, Int, Bool, Int, enum, enum, enum, enum, Bool, Bool ]
!Abc {   name, len,  ovr,  at,  chk,  nrm,  ovf,  unf,  get,  set }
~~~

* `!Abc [ String, Int, Int ]` -> `!Abc { name, at, len }`
* `!Abc [ String, Int, enum ]` -> `!Abc { name, len, chk }`
* `!Abc [ String, Int, enum ]` -> `!Abc { name, len, nrm }`

### `Num`

Incremental constructor

~~~yaml
!Num [ String, Int, Bool, Int, enum, enum, enum, enum, enum ]
!Num {   name, len,  ovr,  at,  acc,  nrm,  ovf,  unf,  wid }
~~~

* `!Num [ String, Int, Int ]` -> `!Num { name, at, len }`
* `!Num [ String, Int, enum ]` -> `!Num { name, len, acc }`
* `!Num [ String, Int, enum ]` -> `!Num { name, len, nrm }`

### `Fil`

Incremental constructor

~~~yaml
!Fil [ Int, Int, char ]
!Fil { len,  at, fill }
~~~

* `!Fil [ Int, Int ]` -> `!Fil { at, len }`

### `Val`

Incremental constructor

~~~yaml
!Val [ String, Int, Int ]
!Val {    val, len,  at }
~~~

### `Dom`

Incremental constructor

~~~yaml
!Dom [ String, Int,  List, Bool, Int ]
!Dom {   name, len, items,  ovr,  at }
~~~

* `!Dom [ String, List ]` -> `!Dom { name, items }`

### `Grp`

Incremental constructor

~~~yaml
!Grp [ String, List, Bool, Int, Int ]
!Grp {   name, flds,  ovr, len,  at }
~~~

* `!Grp [ String, Int, List ]` -> `!Grp { name, len, flds }`
* `!Grp [ String, Int, Int , List ]` -> `!Grp { name, at, len, flds }`
* `!Grp [ String, Int, Bool , List ]` -> `!Grp { name, len, ovr, flds }`
* `!Grp [ String, Int, Int, Bool , List ]` -> `!Grp { name, at, len, ovr, flds }`

### `Occ`

Incremental constructor

~~~yaml
!Occ [ String, Int, List, Bool, Int, Int ]
!Occ {   name,   x, flds,  ovr, len,  at }
~~~

* `!Occ [ String, Int, Int, List ]` -> `!Occ { name, len, x, flds }`
* `!Occ [ String, Int, Int, Int, List ]` -> `!Occ { name, at, len, x, flds }`
* `!Occ [ String, Int, Int, Bool, List ]` -> `!Occ { name, len, x, ovr, flds }`
* `!Occ [ String, Int, Int, Int, Bool, List ]` -> `!Occ { name, at, len, x, ovr, flds }`

### `GRP`

Incremental constructor

~~~yaml
!GRP [ String, Trait|String, Int, Bool, Int ]
!GRP {   name,       as|ref, len,  ovr,  at }
~~~

### `OCC`

Incremental constructor

~~~yaml
!OCC [ String, Int, Trait|String, Int, Bool, Int ]
!OCC {   name,   x,       as|ref, len,  ovr,  at }
~~~

### `Emb`

Incremental constructor

~~~yaml
!Emb [ Trait|String, Int, Int ]
!Emb {      src|ref, len,  at }
~~~
