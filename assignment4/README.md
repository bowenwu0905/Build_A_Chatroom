# Random Sentence Generator
The “Random Sentence Generator” is a piece of technology to create random sentences from a pattern known as a grammar. 
A grammar is a template that describes the various combinations of words that can be used to form valid sentences.

## Usage
Specify a pattern consisting of normal text and "non-terminal" tags in brackets (<>).
A non-terminal is a placeholder that will expand to another sequence of words when generating a poem. In contrast, a "terminal" is a normal word that is not changed to anything else when expanding the grammar.

Once the program reads in the grammar, it will be able to produce random expansions from it. It begins with a single non-terminal with the “Start” label. For a non-terminal, consider its definition, which will contain a set of productions. Choose one of the productions at random. Take the words from the chosen production in sequence, (recursively) expanding any which are themselves nonterminals as it goes. 

## Example
![WX20220326-150333](https://media.github.ccs.neu.edu/user/8131/files/60f2947d-8ddf-4e74-bd5c-b1cb8e915445)

According to this grammar, two possible poems are "The big yellow flowers sigh warily tonight."
and "The slugs portend like waves tonight.". 

## User Interface
When the program starts, a directory name will be provided as an argument. This directory will include several grammar files.

The program will allow the user to choose one of these grammars. When a grammar is chosen, a generated sentence will be displayed.
Ask the user if they would like another one, and continue generating and printing sentences until the user says "n".

Then, go back to the main menu, and allow the user to choose another grammar if they would like. 
If user says "q", exit the program.


```
The following grammars are available:
1. Insults
2. Term Paper Generator
3. Dear John letter
Which would you like to use? (q to quit)
1
With the fury of Thor’s belch, may the hosts of Hades find your
earlobes suddenly delectable.
Would you like another? (y/n)
y
You mutilated goat.
Would you like another? (y/n)
n
The following grammars are available:
1. Insults
2. Term Paper Generator
3. Dear John letter
Which would you like to use? (q to quit)
q
```



