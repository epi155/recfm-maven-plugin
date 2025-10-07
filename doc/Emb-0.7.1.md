## `Emb`

|attribute| alt | type      | O | default |
|---------|:---:|:---------:|:-:|---------|
|offset   | at  | int       |   | itself  |
|length   | len | int       | X |         |
|source   | src | interface | X |         |
|reference| ref | string    | X |         |

The `source` and `reference` attributes are alternatives.
The `source` attribute requires the interface anchor, which must be defined in the same configuration file.
The `reference` attribute requires the interface name, which can be defined in another configuration file.

~~~yaml
packages:
  - name: com.example.recfm

    interfaces:
      - &TransactionArea
        name: ITransactionArea
        length: 12
        fields:
          - !Abc { name: transId   , at:  1, len: 9 }
          - !Num { name: esitoAgg  , at: 10, len: 1 }
          - !Num { name: esitoCompl, at: 11, len: 1 }
          - !Val { val: "\n"       , at: 12, len: 1 }

    classes:
      - name: ByAnchor
        length: 19324
        fields:
          - !Emb { src: *TransactionArea, at:  1, len:    12 }
          - !Fil {                        at: 13, len: 19313 }

      - name: ByName
        length: 19324
        fields:
          - !Emb { ref: ITransactionArea, at:  1, len:    12 }
          - !Fil {                        at: 13, len: 19313 }
~~~