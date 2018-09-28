<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 plus MathML 2.0//EN" "http://www.w3.org/Math/DTD/mathml2/xhtml-math11-f.dtd">
<%@page contentType="application/xhtml+xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@  page 
import="cz.muni.fi.webmias.Indexes"
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:m="http://www.w3.org/1998/Math/MathML" xmlns:svg="http://www.w3.org/2000/svg">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Web Math Indexer and Searcher</title>
        <link type="text/css" rel="stylesheet" href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
        <link type="text/css" rel="stylesheet" href="style.css" />
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.3.min.js"/>
        <script type="text/javascript" src="https://code.jquery.com/ui/1.11.3/jquery-ui.min.js"/>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-AMS-MML_HTMLorMML" />
        <script type="text/javascript" src="webmias.js"/>
    </head> 
    <body>
        <div>
            <div id="links">
                <a href="examples.xhtml" >Examples</a>
                <a href="about.html" >About</a>
                <a href="about.html#help" >Help</a>
                <a href="about.html#contact" >Contact</a>
            </div>
            <div id="main-container">
                <a id="logo" href="${pageContext.request.contextPath}/" />

                <form action="${pageContext.request.contextPath}/ps" method="post"
                      onsubmit="buildQuery(); return checkForm(this)"
                      class="nolinkhighlighting">
                    <div class="query-clauses">
                        <div class="operator">
                            Match
                            <select id="operator">
                                <option value="any">any</option>
                                <option value="all">all</option>
                            </select>
                            of the following rules
                        </div>
                        <div class="query-clause">
                            <select class="field-select" title="">
                                <option value="any">Any field</option>
                                <option value="title">Title</option>
                                <option value="content">Content</option>
                            </select>
                            <input type="text" class="field-input" title=""/>
                        </div>
                    </div>
                    <div>
                        <a href="#" title="Add clause" onclick="addClause()" >Add clause</a>
                    </div>
                    <div class="math-clause">
                        Contains the following formula:<br/>
                        <textarea id="mathQuery" rows="5" title="Math formulae can be entered either in TeX or MathML notation (format will be autodetected). LaTeX math has to be enclosed within $, AMS packages are supported." class="full-width"></textarea>
                    </div>
                    <div id="options" align="right">
                        <div class="search-option">
                            Search using: 
                            <select name="variant">
                                <option value="pcmath" <c:if test="${variant=='pcmath'}">selected="selected"</c:if>>presentation and content</option>
                                <option value="pmath" <c:if test="${variant=='pmath'}">selected="selected"</c:if>>presentation</option>
                                <option value="cmath" <c:if test="${variant=='cmath'}">selected="selected"</c:if>>content</option>
                                </select>
                            </div>
                            <div class="search-option">
                                Search in:
                                <select name="index">
                                    <c:forEach items="<%= Indexes.getIndexNames() %>" var="indexDef" varStatus="status">
                                        <option value="${status.index}" <c:if test="${index==status.index}">selected="selected"</c:if>>${indexDef}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="search-option">
                                <div>
                                    Verbose output:
                                    <input type="checkbox" id="debug" name="debug" value="true" <c:if test="${debug eq true}">checked="checked"</c:if> />
                                </div>
                                <div>
                                    Extract subformulae:
                                    <input type="checkbox" id="extractSubformulae" name="extractSubformulae" value="true" <c:if test="${extractSubformulae eq true}">checked="checked"</c:if> />
                                </div>
                                <div>
                                    Reduce weights of derived formulae:
                                    <input type="checkbox" id="reduceWeighting" name="reduceWeighting" value="true" <c:if test="${reduceWeighting eq true}">checked="checked"</c:if> />
                                </div>
                            </div>
                        </div>
                        <div id="visualQueryContainer">
                            Rendered: 
                            <div id="visualQuery" >
                            </div>
                        </div>
                        <div class="nofloat"></div>
                        <div class="search-button-container">
                            <input type="submit" name="searchButton" value="Search"/>
                            <input type="hidden" name="n" value="0" />
                            <input type="hidden" name="query" />
                            <input type="hidden" name="qc" value="<c:out value="${qc}"/>" />
                        </div>
                    <div class="nofloat"></div>
                </form>
                <c:if test="${debug eq true}">
                    <form>
                        <c:if test="${processedQuery!=null}">
                            <div id="processedQuery">
                                Processed query:
                                <textarea name="query" cols="125" rows="50" readonly="true" ><c:out value="${processedQuery}" escapeXml="true"/></textarea>
                            </div>
                        </c:if>
                        <c:if test="${luceneQuery!=null}">
                            Lucene query:
                            <textarea name="query" cols="125" rows="5" readonly="true" ><c:out value="${luceneQuery}" escapeXml="true"/></textarea>
                        </c:if>
                    </form>
                </c:if>

                <div class="results-container">
                    <c:if test="${!empty nores}">
                        <c:out value="${nores}" />
                        Core searching time: <c:out value="${coreTime} ms"/>
                        Total searching time: <c:out value="${totalTime} ms"/>
                    </c:if>
                    <c:if test="${!empty results}">
                        <span class="info small">Total hits: ${total}, showing ${n*resPerPage+1}–<c:if test="${total<n*resPerPage+resPerPage}">${total}</c:if><c:if test="${total>resPerPage && total>n*resPerPage+resPerPage}"><c:out value="${n*resPerPage+resPerPage}" /></c:if>.
                            Core searching time: <c:out value="${coreTime} ms"/>
                            Total searching time: <c:out value="${totalTime} ms"/></span>
                            <c:forEach items="${results}" var="res">
                            <div class="result">
                                <a class="title" href="<c:if test="${forbidden}">forbidden.jsp</c:if><c:if test="${!forbidden}">${res.id}</c:if>" onclick="GO">
                                    <c:out value="${res.title}" />
                                </a>
                                <div class="snippet">
                                    <c:if test="${!empty res.snippet}">
                                        <div>${res.snippet}</div>
                                    </c:if>
                                </div>
                                <div class="info">${res.info}</div>
                                <span class="id">
                                    <c:if test="${fn:startsWith(res.id, 'http')}">
                                        <a href="${res.id}">
                                    </c:if>
                                    <c:out value="${res.id}"/>
                                    <c:if test="${fn:startsWith(res.id, 'http')}">
                                        </a>
                                    </c:if>
                                </span> -
                                <a class="cached-file" target="_blank" data-url="${res.id}" data-href="<c:if test="${forbidden}">forbidden.jsp</c:if><c:if test="${!forbidden}">cached?index=${index}&amp;path=${res.id}</c:if>">cached XHTML</a>
                                </div>
                        </c:forEach>
                        <div class="nolinkhighlighting">
                        <c:if test="${n - 4 ge 1}">
                            <form action="${pageContext.request.contextPath}/ps" method="post" style="display: inline">
                                <a href="#1" onclick="$(this).closest('form').submit(); return false;">1</a>
                                <span> ... </span>
                                <input type="hidden" name="query" value="<c:out value="${query}"/>" />
                                <input type="hidden" name="variant" value="<c:out value="${variant}"/>" />
                                <input type="hidden" name="index" value="<c:out value="${index}"/>" />
                                <input type="hidden" name="n" value="1" />
                                <input type="hidden" name="qc" value="<c:out value="${qc}"/>" />
                                <c:if test="${debug eq true}">
                                    <input type="hidden" name="debug" value="<c:out value="${debug}"/>" />
                                </c:if>
                                <c:if test="${extractSubformulae eq true}">
                                    <input type="hidden" name="extractSubformulae" value="<c:out value="${extractSubformulae}"/>" />
                                </c:if>
                                <c:if test="${reduceWeighting eq true}">
                                    <input type="hidden" name="reduceWeighting" value="<c:out value="${reduceWeighting}"/>" />
                                </c:if>
                            </form>
                        </c:if>
                        <c:forEach items="${pages}" var="pageNo" begin="${Math.max(n - 4, 0)}" end="${Math.min(n + 4, Math.max(fn:length(pages) - 2, 0))}">
                            <form action="${pageContext.request.contextPath}/ps" method="post" style="display: inline">
                                <a href="#${pageNo}" onclick="$(this).closest('form').submit(); return false;"><c:if test="${pageNo == n + 1}"><b></c:if>${pageNo}<c:if test="${pageNo == n + 1}"></b></c:if></a>
                                <input type="hidden" name="query" value="<c:out value="${query}"/>" />
                                <input type="hidden" name="variant" value="<c:out value="${variant}"/>" />
                                <input type="hidden" name="index" value="<c:out value="${index}"/>" />
                                <input type="hidden" name="n" value="<c:out value="${pageNo}"/>" />
                                <input type="hidden" name="qc" value="<c:out value="${qc}"/>" />
                                <c:if test="${debug eq true}">
                                    <input type="hidden" name="debug" value="<c:out value="${debug}"/>" />
                                </c:if>
                                <c:if test="${extractSubformulae eq true}">
                                    <input type="hidden" name="extractSubformulae" value="<c:out value="${extractSubformulae}"/>" />
                                </c:if>
                                <c:if test="${reduceWeighting eq true}">
                                    <input type="hidden" name="reduceWeighting" value="<c:out value="${reduceWeighting}"/>" />
                                </c:if>
                            </form>
                        </c:forEach>
                        <c:if test="${n + 4 le fn:length(pages) - 3}">
                            <form action="${pageContext.request.contextPath}/ps" method="post" style="display: inline">
                                <span> ... </span>
                                <a href="#<c:out value="${Math.max(fn:length(pages) - 1, 0)}"/>" onclick="$(this).closest('form').submit(); return false;"><c:out value="${Math.max(fn:length(pages) - 1, 0)}"/></a>
                                <input type="hidden" name="query" value="<c:out value="${query}"/>" />
                                <input type="hidden" name="variant" value="<c:out value="${variant}"/>" />
                                <input type="hidden" name="index" value="<c:out value="${index}"/>" />
                                <input type="hidden" name="n" value="<c:out value="${Math.max(fn:length(pages) - 1, 0)}"/>" />
                                <input type="hidden" name="qc" value="<c:out value="${qc}"/>" />
                                <c:if test="${debug eq true}">
                                    <input type="hidden" name="debug" value="<c:out value="${debug}"/>" />
                                </c:if>
                                <c:if test="${extractSubformulae eq true}">
                                    <input type="hidden" name="extractSubformulae" value="<c:out value="${extractSubformulae}"/>" />
                                </c:if>
                                <c:if test="${reduceWeighting eq true}">
                                    <input type="hidden" name="reduceWeighting" value="<c:out value="${reduceWeighting}"/>" />
                                </c:if>
                            </form>
                        </c:if>
                        </div>
                    </c:if>
                </div>
            </div>
            <c:if test="${!empty results}"><hr/></c:if>
        </div>
    </body>
</html>