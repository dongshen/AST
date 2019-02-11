CREATE TABLE AndroidMapping(AndroidMappingId INTEGER PRIMARY KEY, fromFile INTEGER NOT NULL, toFiles INTEGER NOT NULL, kind INTEGER NOT NULL, emit INTEGER NOT NULL, translate INTEGER NOT NULL, build INTEGER NOT NULL);

CREATE TABLE Annotations(AnnotationsId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, infoHashHi INTEGER NOT NULL, infoHashLo INTEGER NOT NULL, info BLOB NOT NULL);

CREATE TABLE BinaryTULookup(BinaryTULookupId INTEGER PRIMARY KEY, emit INTEGER NOT NULL, binaryTU INTEGER NOT NULL, lang INTEGER NOT NULL, isDirect INTEGER NOT NULL);

CREATE TABLE CallgraphFragment(CallgraphFragmentId INTEGER PRIMARY KEY, defnDecls BLOB NOT NULL, externDecls BLOB NOT NULL, defnTypes BLOB NOT NULL, externTypes BLOB NOT NULL, calls BLOB NOT NULL);

CREATE TABLE CiCoverabilityInfo(CiCoverabilityInfoId INTEGER PRIMARY KEY, md5HashHi INTEGER NOT NULL, md5HashLo INTEGER NOT NULL, type INTEGER NOT NULL, note_data BLOB NOT NULL);

CREATE TABLE ClassPathFiles(ClassPathFilesId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, inputFilesHashHi INTEGER NOT NULL, inputFilesHashLo INTEGER NOT NULL, inputFiles BLOB NOT NULL);

CREATE TABLE CmdLineString(CmdLineStringId INTEGER PRIMARY KEY, s TEXT NOT NULL);

CREATE TABLE CompilerOutput(CompilerOutputId INTEGER PRIMARY KEY, md5_hashHi INTEGER NOT NULL, md5_hashLo INTEGER NOT NULL, filename INTEGER NOT NULL, timestamp INTEGER NOT NULL, contents INTEGER NOT NULL);

CREATE TABLE CompilerOutputSet(CompilerOutputSetId INTEGER PRIMARY KEY, info BLOB NOT NULL);

CREATE TABLE CovBuildInvocation(CovBuildInvocationId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, user INTEGER NOT NULL, hostname INTEGER NOT NULL, processId INTEGER NOT NULL, startTime INTEGER NOT NULL, endTime INTEGER NOT NULL, exitCode INTEGER NOT NULL, workingDirectory INTEGER NOT NULL, hostPlatform INTEGER NOT NULL, envvars INTEGER NOT NULL, commandLine BLOB NOT NULL, nativeBuildCmdOffset INTEGER NOT NULL, name TEXT NOT NULL, wasFullBuild INTEGER NOT NULL, sawUpToDateTU INTEGER NOT NULL);

CREATE TABLE CovEmitCsInvocation(CovEmitCsInvocationId INTEGER PRIMARY KEY AUTOINCREMENT, refct INTEGER NOT NULL, user INTEGER NOT NULL, hostname INTEGER NOT NULL, processId INTEGER NOT NULL, startTime INTEGER NOT NULL, endTime INTEGER NOT NULL, exitCode INTEGER NOT NULL, workingDirectory INTEGER NOT NULL, hostPlatform INTEGER NOT NULL, envvars INTEGER NOT NULL, commandLine BLOB NOT NULL, compilerOutput INTEGER NOT NULL, referencedAssemblies INTEGER NOT NULL);

CREATE TABLE CovEmitFortranInvocation(CovEmitFortranInvocationId INTEGER PRIMARY KEY AUTOINCREMENT, refct INTEGER NOT NULL, user INTEGER NOT NULL, hostname INTEGER NOT NULL, processId INTEGER NOT NULL, startTime INTEGER NOT NULL, endTime INTEGER NOT NULL, exitCode INTEGER NOT NULL, workingDirectory INTEGER NOT NULL, hostPlatform INTEGER NOT NULL, envvars INTEGER NOT NULL, commandLine BLOB NOT NULL, referencedModules INTEGER NOT NULL);

CREATE TABLE CovEmitInvocation(CovEmitInvocationId INTEGER PRIMARY KEY, user INTEGER NOT NULL, hostname INTEGER NOT NULL, processId INTEGER NOT NULL, startTime INTEGER NOT NULL, endTime INTEGER NOT NULL, exitCode INTEGER NOT NULL, workingDirectory INTEGER NOT NULL, hostPlatform INTEGER NOT NULL, envvars INTEGER NOT NULL, commandLine BLOB NOT NULL);

CREATE TABLE CovEmitJavaInvocation(CovEmitJavaInvocationId INTEGER PRIMARY KEY AUTOINCREMENT, refct INTEGER NOT NULL, user INTEGER NOT NULL, hostname INTEGER NOT NULL, processId INTEGER NOT NULL, startTime INTEGER NOT NULL, endTime INTEGER NOT NULL, exitCode INTEGER NOT NULL, workingDirectory INTEGER NOT NULL, hostPlatform INTEGER NOT NULL, envvars INTEGER NOT NULL, commandLine BLOB NOT NULL, classPath BLOB NOT NULL, bytecodeOnly INTEGER NOT NULL, lang INTEGER NOT NULL, classPathFiles INTEGER NOT NULL, cpfPartialOrder TEXT NOT NULL);

CREATE TABLE CovEmitSwiftInvocation(CovEmitSwiftInvocationId INTEGER PRIMARY KEY AUTOINCREMENT, refct INTEGER NOT NULL, user INTEGER NOT NULL, hostname INTEGER NOT NULL, processId INTEGER NOT NULL, startTime INTEGER NOT NULL, endTime INTEGER NOT NULL, exitCode INTEGER NOT NULL, workingDirectory INTEGER NOT NULL, hostPlatform INTEGER NOT NULL, envvars INTEGER NOT NULL, commandLine BLOB NOT NULL, referencedModules INTEGER NOT NULL);

CREATE TABLE CoveragePattern(CoveragePatternId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, infoHashHi INTEGER NOT NULL, infoHashLo INTEGER NOT NULL, info BLOB NOT NULL);

CREATE TABLE CovInstrumentStats(CovInstrumentStatsId INTEGER PRIMARY KEY, covbuildInvocationId INTEGER NOT NULL, goalName TEXT NOT NULL, sitesAttempted INTEGER NOT NULL, sitesInstrumented INTEGER NOT NULL);

CREATE TABLE CovTranslateInvocation(CovTranslateInvocationId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, user INTEGER NOT NULL, hostname INTEGER NOT NULL, processId INTEGER NOT NULL, startTime INTEGER NOT NULL, endTime INTEGER NOT NULL, exitCode INTEGER NOT NULL, workingDirectory INTEGER NOT NULL, hostPlatform INTEGER NOT NULL, envvars INTEGER NOT NULL, commandLine BLOB NOT NULL, nativeCompileCmdOffset INTEGER NOT NULL);

CREATE TABLE DeclaredClasses(DeclaredClassesId INTEGER PRIMARY KEY, defnDecls BLOB NOT NULL, externDecls BLOB NOT NULL, rjts BLOB NOT NULL);

CREATE TABLE DeclaredEnums(DeclaredEnumsId INTEGER PRIMARY KEY, info BLOB NOT NULL);

CREATE TABLE DeclaredGlobals(DeclaredGlobalsId INTEGER PRIMARY KEY, defnDecls BLOB NOT NULL, externDecls BLOB NOT NULL, defnTypes BLOB NOT NULL, externTypes BLOB NOT NULL, inits BLOB NOT NULL);

CREATE TABLE DeclaredTypeAliases(DeclaredTypeAliasesId INTEGER PRIMARY KEY, info BLOB NOT NULL);

CREATE TABLE DefinedClassInfo(DefinedClassInfoId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, infoHashHi INTEGER NOT NULL, infoHashLo INTEGER NOT NULL, info BLOB NOT NULL);

CREATE TABLE DefinedEnumInfo(DefinedEnumInfoId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, infoHashHi INTEGER NOT NULL, infoHashLo INTEGER NOT NULL, info BLOB NOT NULL);

CREATE TABLE DefinedFunctionInfo(DefinedFunctionInfoId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, workerInfoHashHi INTEGER NOT NULL, workerInfoHashLo INTEGER NOT NULL, workerInfo BLOB NOT NULL, masterInfo BLOB NOT NULL);

CREATE TABLE DefinedGlobalInfo(DefinedGlobalInfoId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, workerInfoHashHi INTEGER NOT NULL, workerInfoHashLo INTEGER NOT NULL, workerInfo BLOB NOT NULL, masterInfo BLOB NOT NULL);

CREATE TABLE EnvironmentVar(EnvironmentVarId INTEGER PRIMARY KEY, s TEXT NOT NULL);

CREATE TABLE EnvironmentVars(EnvironmentVarsId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, varsHashHi INTEGER NOT NULL, varsHashLo INTEGER NOT NULL, vars BLOB NOT NULL);

CREATE TABLE FileContents(FileContentsId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, contentsHashHi INTEGER NOT NULL, contentsHashLo INTEGER NOT NULL, contents BLOB NOT NULL, filename INTEGER NOT NULL, timestamp INTEGER NOT NULL, contentSize INTEGER NOT NULL, blankLines INTEGER NOT NULL, commentLines INTEGER NOT NULL, codeLines INTEGER NOT NULL, inlineCommentLines INTEGER NOT NULL, scmAnnotations INTEGER NOT NULL);

CREATE TABLE FileName(FileNameId INTEGER PRIMARY KEY, parent INTEGER NOT NULL, component TEXT NOT NULL, case_preserved_component TEXT NOT NULL);

CREATE TABLE FileNameAliasSet(FileNameAliasSetId INTEGER PRIMARY KEY, aliasesHashHi INTEGER NOT NULL, aliasesHashLo INTEGER NOT NULL, aliases BLOB NOT NULL);

CREATE TABLE FileTestcoverage(FileTestcoverageId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, file_contents INTEGER NOT NULL, coverableHashHi INTEGER NOT NULL, coverableHashLo INTEGER NOT NULL, total_coverageHashHi INTEGER NOT NULL, total_coverageHashLo INTEGER NOT NULL, test_specific_coverage BLOB NOT NULL);

CREATE TABLE FundamentalTypeInfo(FundamentalTypeInfoId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, infoHashHi INTEGER NOT NULL, infoHashLo INTEGER NOT NULL, info BLOB NOT NULL);

CREATE TABLE HostName(HostNameId INTEGER PRIMARY KEY, s TEXT NOT NULL);

CREATE TABLE ImportRef(ImportRefId INTEGER PRIMARY KEY, importString TEXT NOT NULL);

CREATE TABLE ImportRefSet(ImportRefSetId INTEGER PRIMARY KEY, info BLOB NOT NULL);

CREATE TABLE InputFile(InputFileId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, md5_hashHi INTEGER NOT NULL, md5_hashLo INTEGER NOT NULL, filename INTEGER NOT NULL, package INTEGER NOT NULL, timestamp INTEGER NOT NULL, xrefs INTEGER NOT NULL, ppinfo INTEGER NOT NULL, messages INTEGER NOT NULL, contents INTEGER NOT NULL, encoding INTEGER NOT NULL, annotations INTEGER NOT NULL, filefound INTEGER NOT NULL, blankLines INTEGER NOT NULL, commentLines INTEGER NOT NULL, codeLines INTEGER NOT NULL, inlineCommentLines INTEGER NOT NULL, type INTEGER NOT NULL, origin INTEGER NOT NULL, jar_info BLOB NOT NULL);

CREATE TABLE InputFileEncoding(InputFileEncodingId INTEGER PRIMARY KEY, s TEXT NOT NULL);

CREATE TABLE InputFileSet(InputFileSetId INTEGER PRIMARY KEY, info BLOB NOT NULL);

CREATE TABLE JspDocument(JspDocumentId INTEGER PRIMARY KEY, jspFile INTEGER NOT NULL, domData BLOB NOT NULL);

CREATE TABLE MangledName(MangledNameId INTEGER PRIMARY KEY, s TEXT NOT NULL);

CREATE TABLE ModuleLink(ModuleLinkId INTEGER PRIMARY KEY, linkString TEXT NOT NULL, framework INTEGER NOT NULL, filename INTEGER NOT NULL);

CREATE TABLE ModuleLinkSet(ModuleLinkSetId INTEGER PRIMARY KEY, info BLOB NOT NULL);

CREATE TABLE OtherEmitInvocation(OtherEmitInvocationId INTEGER PRIMARY KEY AUTOINCREMENT, refct INTEGER NOT NULL, user INTEGER NOT NULL, hostname INTEGER NOT NULL, processId INTEGER NOT NULL, startTime INTEGER NOT NULL, endTime INTEGER NOT NULL, exitCode INTEGER NOT NULL, workingDirectory INTEGER NOT NULL, hostPlatform INTEGER NOT NULL, envvars INTEGER NOT NULL, commandLine BLOB NOT NULL, processInvocationKind INTEGER NOT NULL);

CREATE TABLE OtherHosts(OtherHostsId INTEGER PRIMARY KEY, host INTEGER NOT NULL, timestamp INTEGER NOT NULL);

CREATE TABLE ParseMessages(ParseMessagesId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, infoHashHi INTEGER NOT NULL, infoHashLo INTEGER NOT NULL, info BLOB NOT NULL);

CREATE TABLE PPInfo(PPInfoId INTEGER PRIMARY KEY, info BLOB NOT NULL);

CREATE TABLE ReplacementSet(ReplacementSetId INTEGER PRIMARY KEY, replacementKey INTEGER NOT NULL, timestamp INTEGER NOT NULL, primarySourceFiles INTEGER NOT NULL);

CREATE TABLE ScmAnnotations(ScmAnnotationsId INTEGER PRIMARY KEY, scmDetailsHashHi INTEGER NOT NULL, scmDetailsHashLo INTEGER NOT NULL, scmDetails BLOB NOT NULL, fileContentsIdHashHi INTEGER NOT NULL, fileContentsIdHashLo INTEGER NOT NULL);

CREATE TABLE ScmAuthor(ScmAuthorId INTEGER PRIMARY KEY, s TEXT NOT NULL);

CREATE TABLE ScmChangeRecord(ScmChangeRecordId INTEGER PRIMARY KEY, timestamp INTEGER NOT NULL, author INTEGER NOT NULL, revision TEXT NOT NULL, revision_display TEXT NOT NULL, md5HashHi INTEGER NOT NULL, md5HashLo INTEGER NOT NULL, username INTEGER NOT NULL);

CREATE TABLE TestCaptureRun(TestCaptureRunId INTEGER PRIMARY KEY, timestamp INTEGER NOT NULL, buildID TEXT NOT NULL, successful INTEGER NOT NULL, captureTag TEXT NOT NULL, isCurrent INTEGER NOT NULL);

CREATE TABLE TestSuiteRun(TestSuiteRunId INTEGER PRIMARY KEY, refct INTEGER NOT NULL, whenrun INTEGER NOT NULL, testCaptureRunId INTEGER NOT NULL, suitename TEXT NOT NULL, testname TEXT NOT NULL, status TEXT NOT NULL, msDuration INTEGER NOT NULL, sourceFilename INTEGER NOT NULL, srcLine INTEGER NOT NULL);

CREATE TABLE TimeStamp(stamp INTEGER NOT NULL);

CREATE TABLE TranslationUnit(TranslationUnitId INTEGER PRIMARY KEY, emit INTEGER NOT NULL, translate INTEGER NOT NULL, build INTEGER NOT NULL, replayFromEmitBuild INTEGER NOT NULL, sourceFiles INTEGER NOT NULL, primarySF INTEGER NOT NULL, fileNameAliases INTEGER NOT NULL, callgraph INTEGER NOT NULL, classes INTEGER NOT NULL, globals INTEGER NOT NULL, enums INTEGER NOT NULL, jspDom INTEGER NOT NULL, typeAliases INTEGER NOT NULL, ftinfo INTEGER NOT NULL, mspchPPFile INTEGER NOT NULL, mspch INTEGER NOT NULL, compilerOutputs INTEGER NOT NULL, moduleLinks INTEGER NOT NULL, importRefs INTEGER NOT NULL, cmdLineID INTEGER NOT NULL, lang INTEGER NOT NULL, customLang TEXT NOT NULL, isStub INTEGER NOT NULL, hasComplementaryInfo INTEGER NOT NULL, fromCovImportResults INTEGER NOT NULL, isTransientTU INTEGER NOT NULL, caseSensitive INTEGER NOT NULL, couldRequireRegen INTEGER NOT NULL, mspchIncludeLineNo INTEGER NOT NULL, isFailure INTEGER NOT NULL, isCreateEDGPCH INTEGER NOT NULL, hadRecoverableErrors INTEGER NOT NULL, isFromBootClassPath INTEGER NOT NULL, isPendingDeletion INTEGER NOT NULL, addToNextReplacementSet INTEGER NOT NULL);

CREATE TABLE UserName(UserNameId INTEGER PRIMARY KEY, s TEXT NOT NULL);

CREATE TABLE version(version INTEGER NOT NULL);

CREATE TABLE XinvocXref(XinvocXrefId INTEGER PRIMARY KEY, fullyQualifiedNameHashHi INTEGER NOT NULL, fullyQualifiedNameHashLo INTEGER NOT NULL, fullyQualifiedName TEXT NOT NULL, filename INTEGER NOT NULL, lineNo INTEGER NOT NULL);

CREATE TABLE Xrefs(XrefsId INTEGER PRIMARY KEY, info BLOB NOT NULL);

CREATE INDEX AndroidMapping_fromFile_index ON AndroidMapping (fromFile);

CREATE INDEX Annotations_infoHashHi_index ON Annotations (infoHashHi);

CREATE INDEX BinaryTULookup_binaryTU_index ON BinaryTULookup (binaryTU);

CREATE INDEX BinaryTULookup_emit_lang_index ON BinaryTULookup (emit, lang);

CREATE INDEX CiCoverabilityInfo_md5HashHi_md5HashLo_type_index ON CiCoverabilityInfo (md5HashHi, md5HashLo, type);

CREATE INDEX ClassPathFiles_inputFilesHashHi_index ON ClassPathFiles (inputFilesHashHi);

CREATE INDEX CmdLineString_s_index ON CmdLineString (s);

CREATE INDEX CompilerOutput_filename_md5_hashHi_index ON CompilerOutput (filename, md5_hashHi);

CREATE INDEX CoveragePattern_infoHashHi_index ON CoveragePattern (infoHashHi);

CREATE INDEX CovInstrumentStats_covbuildInvocationId_index ON CovInstrumentStats (covbuildInvocationId);

CREATE INDEX DefinedClassInfo_infoHashHi_index ON DefinedClassInfo (infoHashHi);

CREATE INDEX DefinedEnumInfo_infoHashHi_index ON DefinedEnumInfo (infoHashHi);

CREATE INDEX DefinedFunctionInfo_workerInfoHashHi_index ON DefinedFunctionInfo (workerInfoHashHi);

CREATE INDEX DefinedGlobalInfo_workerInfoHashHi_index ON DefinedGlobalInfo (workerInfoHashHi);

CREATE INDEX EnvironmentVars_varsHashHi_index ON EnvironmentVars (varsHashHi);

CREATE INDEX EnvironmentVar_s_index ON EnvironmentVar (s);

CREATE INDEX FileContents_contentsHashHi_index ON FileContents (contentsHashHi);

CREATE INDEX FileContents_filename_timestamp_contentSize_index ON FileContents (filename, timestamp, contentSize);

CREATE INDEX FileNameAliasSet_aliasesHashHi_index ON FileNameAliasSet (aliasesHashHi);

CREATE INDEX FileName_component_parent_index ON FileName (component, parent);

CREATE INDEX FileTestcoverage_file_contents_index ON FileTestcoverage (file_contents);

CREATE INDEX FundamentalTypeInfo_infoHashHi_index ON FundamentalTypeInfo (infoHashHi);

CREATE INDEX HostName_s_index ON HostName (s);

CREATE INDEX ImportRef_importString_index ON ImportRef (importString);

CREATE INDEX InputFileEncoding_s_index ON InputFileEncoding (s);

CREATE INDEX InputFile_filename_md5_hashHi_index ON InputFile (filename, md5_hashHi);

CREATE INDEX JspDocument_jspFile_index ON JspDocument (jspFile);

CREATE INDEX MangledName_s_index ON MangledName (s);

CREATE INDEX ModuleLink_linkString_framework_index ON ModuleLink (linkString, framework);

CREATE INDEX OtherHosts_host_index ON OtherHosts (host);

CREATE INDEX ParseMessages_infoHashHi_index ON ParseMessages (infoHashHi);

CREATE INDEX ReplacementSet_replacementKey_index ON ReplacementSet (replacementKey);

CREATE INDEX ScmAnnotations_scmDetailsHashHi_scmDetailsHashLo_index ON ScmAnnotations (scmDetailsHashHi, scmDetailsHashLo);

CREATE INDEX ScmAuthor_s_index ON ScmAuthor (s);

CREATE INDEX ScmChangeRecord_md5HashHi_md5HashLo_index ON ScmChangeRecord (md5HashHi, md5HashLo);

CREATE INDEX TestCaptureRun_timestamp_buildID_captureTag_index ON TestCaptureRun (timestamp, buildID, captureTag);

CREATE INDEX TestSuiteRun_suitename_testname_testCaptureRunId_index ON TestSuiteRun (suitename, testname, testCaptureRunId);

CREATE INDEX TranslationUnit_build_index ON TranslationUnit (build);

CREATE INDEX TranslationUnit_emit_index ON TranslationUnit (emit);

CREATE INDEX TranslationUnit_mspchPPFile_index ON TranslationUnit (mspchPPFile);

CREATE INDEX TranslationUnit_primarySF_index ON TranslationUnit (primarySF);

CREATE INDEX UserName_s_index ON UserName (s);

CREATE INDEX XinvocXref_fullyQualifiedNameHashHi_index ON XinvocXref (fullyQualifiedNameHashHi);

