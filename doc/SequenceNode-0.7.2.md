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

### `Num`

Incremental constructor

~~~yaml
!Num [ String, Int, Bool, Int, enum, enum, enum, enum, enum ]
!Num {   name, len,  ovr,  at,  acc,  nrm,  ovf,  unf,  wid }
~~~

### `Fil`

Incremental constructor

~~~yaml
!Fil [ Int, Int, char ]
!Fil { len,  at, fill }
~~~

### `Val`

Incremental constructor

~~~yaml
!Val [ String, Int, Int ]
!Val {    val, len,  at }
~~~

### `Grp`

Incremental constructor

~~~yaml
!Grp [ String, List, Int, Bool, Int ]
!Grp {   name, flds, len,  ovr,  at }
~~~

### `Occ`

Incremental constructor

~~~yaml
!Occ [ String, Int, List, Int, Bool, Int ]
!Occ {   name,   x, flds, len,  ovr,  at }
~~~

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
