<%@ include file="../fragments/taglibs.jsp" %>

<!DOCTYPE html>
<html>

<jsp:include page="../fragments/header.jsp"/>
<body>

<div class="container">
    <h1>${empty searchList ? 'There is no one result for such query' : ''}</h1>
    <c:forEach var="searchResult" items="${searchList}" varStatus="loop">
        <h3><a href="${searchResult.url}"> ${searchResult.title}</a></h3>
        <div class="s">
            <cite class="_Rm">${searchResult.url}</cite>
        </div>
        <span class="st">${searchResult.line}</span>
        <p></p>
    </c:forEach>
</div>

</body>
</html>