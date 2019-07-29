
# JCR Utils

A collection of useful classes in conjunction with JCR, specifically its reference implementation Apache Jackrabbit.

## Licence

BSD License. Use at your own risk, without express or implied warranty.

## What?

Additional predicate classes - these are generally useful, and can also be composed into "networks" of predicates to realize fairly complex queries on JCR structures.

JCR2TXT and TXT2JCR - transforms JCR Nodes into a compact line-oriented textual format with stable property order, and back. This can be used to make JCR structures comparable using diff and similar tools.

Bean2Node - transforms java beans to JCR using a few simple annotations to configure the transformation process. A counterpart to the Node2Bean tools found in JCR. Can be used to serialize fairly arbitrary java object structures to JCR (and back via Node2Bean) and in conjunction with jax-rs, javax.validation, jackson and similar annotation based APIs operating on beans it can be used to implement a fairly lightweight annotation-driven approach to storing, loading, validating, transforming and transmitting JCR data.

JcrImportingParseEventHandler / JCRWritingParseEventHandler - in case you have been wondering how to (efficiently) move large amounts of data into JCR.

## Development Status

In (slow) development - parts of this code have seen years of active use, while other parts are largely untested. YMMV. 

Pull requests, suggestions for improvement and bug reports are appreciated, please use github.

This project has moved to github from bitbucket, see https://bitbucket.org/jaardvark/jcr-utils for more history.




