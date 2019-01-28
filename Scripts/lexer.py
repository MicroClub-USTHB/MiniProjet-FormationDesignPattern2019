import sys 
import ply.lex as lex



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
t_NEW_LINE = r'\n'
t_EQUALS = r'\='
t_PAR_OUVRANTE = r'\('
t_PAR_FERMANTE = r'\)'

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
    

for i in lines :
 lexer.input(i)

 while True:
        tok = lex.token()
        if not tok:
            break
        print(tok)
