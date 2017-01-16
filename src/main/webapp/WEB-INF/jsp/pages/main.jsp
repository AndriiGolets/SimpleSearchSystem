<%@ include file="../fragments/taglibs.jsp" %>

<!DOCTYPE html>
<html>

<jsp:include page="../fragments/header.jsp"/>

<body>

<spring:url value="/search" var="userActionUrl"/>

<div class="container">
    <div class="row">
        <div class="col-xs-offset-3 col-xs-6">
            <form:form method="POST" modelAttribute="searchQuery" action="${userActionUrl}" class="form-horizontal">

                <div class="form-group">
                    <label class="col-xs-12">Query fo search:</label>
                    <div class="col-xs-12">
                        <c:if test="${status != 'NEW'}"> <form:input path="query" placeholder="Enter query fo search:"
                                                                     class="form-control"/> </c:if>
                        <c:if test="${status == 'NEW'}"> <input type="text" placeholder="Indexation not started!"
                                                                class="form-control" disabled/> </c:if>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-12">
                        <button ${status == 'NEW' ? 'disabled' : ''} type="submit" name="search"
                                                                     class="btn btn-primary btn-block" id="search">
                            Search
                        </button>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-12">
                        <button type="submit" name="index" class="btn btn-primary btn-block" id="go-index">
                            Go to Index Page
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <p></p>
            <h3><a href="#">Link to GitHub</a></h3>
            <p></p>
            <h3> Program description <b>"Simple GOOGLE clone"</b>:</h3>
            <p></p>
            <p> First, go to the index page and start indexing, After indexing has been started, you can enter search
                query and press search button
            </p>

        </div>
    </div>

</div>

</body>
</html>