
grammar DSLSQL; //声明语法头，类似于java类的定义

//在运行脚本后，生成的类中自动带这个包路径
@header {
  package test.dsl.parser;
}



//---------自定义规则---------
statement
    : (sql ender)* ; //定义statement规则，包含0个或多个sql ender组合

sql //定义sql规则，包含LOAD和SELECT两个语法
    : LOAD format '.' path OPTIONS? expression? booleanExpression* AS tableName
    | SELECT ~(';')* AS tableName
    ;

ender :';' ;//定义ender规则为分号

//定义一些关键字的lexer，忽略大小写
AS: [Aa][Ss];
LOAD: [Ll][Oo][Aa][Dd];
SELECT: [Ss][Ee][Ll][Ee][Cc][Tt];
OPTIONS: [Oo][Pp][Tt][Ii][Oo][Nn][Ss];

booleanExpression : 'and' expression ;

expression : qualifiedName '=' STRING ;

format : identifier ;

path : identifier ;

tableName : identifier ;

qualifiedName
    : identifier ('.' identifier)*
    ;

//引用IDENTIFIER或BACKQUOTED_IDENTIFIER规则
identifier
    : IDENTIFIER
    | BACKQUOTED_IDENTIFIER
    ;
//---------自定义规则---------

//---------基础规则---------
//匹配带单引号和双引号的文本
STRING
    : '\'' ( ~('\''|'\\') | ('\\' .) )* '\''
    | '"' ( ~('"'|'\\') | ('\\' .) )* '"'
    ;

//匹配在``中的文本
BACKQUOTED_IDENTIFIER
    : '`' ( ~'`' | '``' )* '`'
    ;

//匹配数字
fragment DIGIT : [0-9] ;

//匹配字母
fragment LETTER : [a-zA-Z] ;

//匹配有数字、字母或下划线组合的文本
IDENTIFIER : (LETTER | DIGIT | '_')+ ;
//---------基础规则---------

//---------通用规则---------
//忽略多行注释
BRACKETED_EMPTY_COMMENT : '/**/' -> channel(HIDDEN) ;

//忽略多行注释
BRACKETED_COMMENT : '/*' ~[+] .*? '*/' -> channel(HIDDEN) ;

//忽略空白符
WS : [ \r\n\t]+ -> channel(HIDDEN) ;

//匹配上述lexer无法匹配的文本
UNRECOGNIZED : . ;
//---------通用规则---------