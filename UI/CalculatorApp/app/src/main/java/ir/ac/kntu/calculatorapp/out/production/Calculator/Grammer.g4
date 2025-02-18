<expression> ::= <term> (("+" | "-") <term>)*
<term> ::= <factor> (("*" | "/") <factor>)*
<factor> ::= <number> | "(" <expression> ")"
<number> ::= [0-9]+
