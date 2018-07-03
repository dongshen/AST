AST
===

AST


Coverity AST parse
===
It will parse Coverity export ast which export by: 
cov-manage-emit --dir {output foler} --tu {tu Number} find . --print-definitions 
cov-manage-emit --dir {output foler} --tu {tu Number} find . --print-debug

Reference coverity command cov-manage-emit related information as below:

Listing emit database information
The find sub-command lists information stored in the emit DB such as symbol names, locations, and definitions. By default, all translation units are included in the results. You can optionally restrict the translation units used in these operations with the --tu and/or --tu-pattern options.

What is being matched by the regular expression (regex) is, in C++, the mangled name of the symbol (according to the IA64 C++ ABI, see http://mentorembedded.github.io/cxx-abi/abi-examples.html#mangling ), of which the actual identifier is always a substring. In C, what is matched is just the identifier.

find <regular expression> [OPTIONS]
There are four kinds of symbols: functions, classes, global variables, and enumerations. If you specify

find <regular expression> 
then the listing for the matching regex command is the symbol name, the kind of entity, the declaration location, and the definition TU.
You can control the information that is returned by using the following options:

--kind {f | c | e | g}
Restricts the search to certain types of entities. The choices are f, c, e and g, for function, class, enum, and global, respectively.

--print-callees
For a function, lists the set of functions it calls. Does not list information on other entities.

--print-definitions
Lists the entity's definition syntax by pretty-printing the AST definition.

--print-debug
Lists the entity's AST definition in debug (indented tree) mode.

