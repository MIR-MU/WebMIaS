<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 plus MathML 2.0//EN" "http://www.w3.org/Math/DTD/mathml2/xhtml-math11-f.dtd">
<%@page contentType="application/xhtml+xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:m="http://www.w3.org/1998/Math/MathML" xmlns:svg="http://www.w3.org/2000/svg">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Web Math Indexer and Searcher</title>
        <link type="text/css" rel="stylesheet" href="style.css" />
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.3.min.js"/>
        <script type="text/javascript" src="https://code.jquery.com/ui/1.11.3/jquery-ui.min.js"/>
        <script type="text/javascript" src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML" />
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
                <a id="logo" href="ps?n=-1"></a>
                <form action="${pageContext.request.contextPath}/ps" method="get"
                      onsubmit="return checkForm(this)">
                    <div>
                        <textarea id="mathQuery" name="query" rows="8" class="full-width"><c:out value="${query}"/></textarea>
                        <div id="options">
                            Search using: 
                            <select name="variant">
                                <option value="pcmath" <c:if test="${variant=='pcmath'}">selected="selected"</c:if>>presentation and content</option>
                                <option value="pmath" <c:if test="${variant=='pmath'}">selected="selected"</c:if>>presentation</option>
                                <option value="cmath" <c:if test="${variant=='cmath'}">selected="selected"</c:if>>content</option>
                                </select>
                                Search in:
                                <select name="index">
                                    <c:forEach items="${indexes}" var="indexDef" varStatus="status">
                                        <option value="${status.index}" <c:if test="${index==status.index}">selected="selected"</c:if>>${indexDef}</option>
                                    </c:forEach>
                                </select>
                                Debug:
                                <input type="checkbox" id="debug" name="debug" value="true" <c:if test="${debug eq true}">checked="checked"</c:if> />
                                <input type="submit" name="searchButton" value="Search"/>
                                <input type="hidden" name="n" value="0" />
                            </div>
                        </div>
                    </form>
                    <div id="visualQueryContainer">
                        Your query: 
                        <div id="visualQuery" >
                        </div>
                    </div>
                    <div class="nofloat"></div>
                <c:if test="${convertedCanonQuery!=null}">
                    <div id="convertedCanonicalizedQuery">
                        Converted and canonicalized query:
                        <textarea name="query" cols="125" rows="8" readonly="true" ><c:out value="${convertedCanonQuery}" escapeXml="true"/></textarea>
                    </div>
                </c:if>
                <c:if test="${luceneQuery!=null}">
                    Lucene query:
                    <textarea name="query" cols="125" rows="5" readonly="true" ><c:out value="${luceneQuery}" escapeXml="true"/></textarea>
                </c:if>
                <div>

                    <c:if test="${!empty nores}">
                        <c:out value="${nores}" />
                        Core searching time: <c:out value="${coreTime} ms"/>
                        Total searching time: <c:out value="${totalTime} ms"/>
                    </c:if>
                    <c:if test="${!empty results}">
                        <span class="info small">Total hits: ${total}, showing ${n*30+1}-<c:if test="${total<n*30+30}">${total}</c:if>
                            <c:if test="${total>30 && total>n*30+30}"><c:out value="${n*30+30}" /></c:if>.                            
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
                                <a class="cached-file" href="<c:if test="${forbidden}">forbidden.jsp</c:if><c:if test="${!forbidden}">${res.id}</c:if>">cached XHTML</a>
                                </div>
                        </c:forEach>
                        <c:forEach items="${pages}" var="pageNo">
                            <c:url var="pageUrl" value="ps">
                                <c:param name="query" value="${query}" />
                                <c:param name="index" value="${index}" />
                                <c:param name="n" value="${pageNo}" />
                            </c:url>
                            <a href="<c:out value="${pageUrl}"/>">${pageNo}</a>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
            <c:if test="${!empty results}"><hr/></c:if>
        </div>
    </body>
</html>
