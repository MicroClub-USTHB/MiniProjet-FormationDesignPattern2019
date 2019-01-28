
import ply.yacc as yacc
import ply.lex as lex
import sys


#LEXER

tokens = [
    
    'VAR',
    'CONST',
    'NON',
    'OR',
    'AND',
    'ARROW',
    'EQUALS',
    'NEW_LINE',
    'TRUE',
    'PAR_OUVRANTE',
    'PAR_FERMANTE',
    'FALSE',
    'SEPARATEUR'
]
t_ignore = r' '
t_EQUALS = r'\='
t_PAR_OUVRANTE = r'\('
t_PAR_FERMANTE = r'\)'

def t_NEW_LINE(t):
    r'\n'
    return t

def t_SEPARATEUR(t):
    r'_'
    return t

def t_TRUE(t):
    r'TRUE|true|True|1'
    t.value = True
    return t

def t_FALSE(t):
    r'FALSE|false|False|0'
    t.value = False
    return t

def t_OR(t):
    r'OR|or|\|\|'
    return t

def t_AND(t):
    r'AND|and|&&'
    return t

def t_NON(t):
    r'NON|non'
    return t

def t_ARROW(t):
    r'\->'
    return t

def t_CONST(t):
    r'CONST'
    return t
    
def t_VAR(t):
    r'[a-zA-Z_][a-zA-Z_]*'
    t.type = 'VAR'
    return t



def t_error(t):
    print('Illegal character !')
    t.lexer.skip(1)

lexer = lex.lex()

precedence = (
    ('left','ARROW'),
    ('left', 'OR'),
    ('left', 'AND'),
    ('left','NON'),
    ('left', 'PAR_OUVRANTE')
)
#PARSER


def p_instructions(p):
    '''
      instructions : expression
                    | var_assign
    '''
    print(p[1])

def p_var_assign(p):
    '''
    var_assign : VAR EQUALS expression
    '''
    p[0] = (p[2],p[1],p[3])

def p_expression(p):
    '''
     expression : expression AND expression
                 | expression OR expression
                 | expression ARROW  expression
                 | NON expression
                 | PAR_OUVRANTE expression PAR_FERMANTE
    '''
    if p[1] != '(':
     if (p[1] == 'NON' or p[1] =='non'):
        p[0] = (p[1],p[2],' ')
     else :
        p[0] = (p[2],p[1],p[3])
    else :
        p[0] = p[2]



def p_expression_true_false(p):
    '''
    expression : TRUE
               | FALSE
    '''
    p[0] = p[1]

def p_expression_var(p):
    '''
    expression : VAR 
               | CONST SEPARATEUR VAR
    '''
    if p[1] == 'CONST':
        p[0] = ('const',p[3])
    else : 
        p[0]=('var',p[1])

def p_error(p):
    print('Syntax Error !')


f = open("data.txt","r")
lines = f.readlines()
i=0
while i < len(lines) : 
    if lines[i] == '\n':
        lines[i] = ' '
    if "\n" in lines[i]:
        s = lines[i]
        s = s[0:len(s)-1]
        lines[i] = s
    i=i+1


parser = yacc.yacc()
for i in lines:
    if i != ' ':
         parser.parse(i)