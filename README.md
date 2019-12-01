# CORE-Language-Impl
DESCRIPTION
This program was written as part of a programming assignment during my senior year to learn more about how a programming language is designed. We were given a grammar for the CORE programming language (a simple programming language with basic logic, variables, and input and output, see CORE.pdf) and were tasked with doing the following:
1) Write a tokenizer that would take in a file containing source code for this CORE language and tokenize it. So keywords like "if" and special symbols like "=" would be turned into an integer representation.
2) Write a parser that would read in from the tokenizer and then be able to "pretty print" the program back out with uniform spacing and indentation.
3) Write a execution function that would actually run the CORE program.

DESIGN DECISIONS
We were given the choice on how to store the structure of the CORE program as either an array-like or a tree-like structure. Seeing as how the grammar was already in a tree-like structure, I decided to go that route and I think that overall it was a good choice. The resulting code seemed cleaner and easier to understand in a more node/tree-like structure.
We were also given an extra credit assignment to implement better error messages from the tokenizer that would include line numbers and better descriptions. I decided to take on this challenge as better error messages are always a good thing to have in my opinion and I figured it could help with debugging incase I had a bug in my code. While I believe that this was an overall good choice, it did lead to my one outstanding bug at the end (see below).

OUTSTANDING BUGS
Sometimes the line numbers for error codes are off by one. The only way to fix this would be to completely redo Tokenizer and check for whitespace before a token rather than after it.
