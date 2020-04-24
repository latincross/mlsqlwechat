grammar Calculator;

@header {
  package calculator.dsl.parser;
}

//EOF表示End of File，也可以理解为输入结束开始执行
line: expr EOF ;
expr: expr (PLUS|MINUS) expr      # AddSub
|INT                              # Int
;

PLUS:'+';
MINUS:'-';
INT:[1-9]+;

WS: [ \r\n\t]+ -> channel(HIDDEN);
UNRECOGNIZED : .;