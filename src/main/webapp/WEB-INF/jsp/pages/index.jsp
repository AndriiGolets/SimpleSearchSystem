<%@ include file="../fragments/taglibs.jsp" %>

<!DOCTYPE html>
<html>

<jsp:include page="../fragments/header.jsp"/>

<body>

<spring:url value="/index" var="userActionUrl"/>

<div class="container">
    <div class="col-xs-offset-3 col-xs-6">
        <form:form method="post" modelAttribute="indexForm" action="${userActionUrl}" class="form-horizontal">
            <div class="form-group">
                <label for="index-url" class="col-xs-12">Url for starting indexation:</label>
                <div class="col-xs-12">
                    <form:input type="text" id="index-url" path="url" placeholder="Enter url for indexing"
                                class="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-6">Recursion deep (3 max):</label>
                <div class="col-xs-3">
                    <form:radiobutton path="recDeep" value="1"/>1
                    <form:radiobutton path="recDeep" value="2"/>2
                    <form:radiobutton path="recDeep" value="3"/>3
                </div>
            </div>

            <div class="form-group">
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-primary btn-block" id="submit-btn">
                      ${indexStart ? 'Go to search page' : 'Start Indexing'}
                    </button>
                </div>
            </div>

            <div class="index-info">
                <div class="form-group">
                    <div class="col-xs-12">
                        <label class="col-xs-6">Indexation status: </label>
                    </div>
                </div>
                <div class="col-xs-offset-1 col-xs-11">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <label class="col-xs-6">Urls Indexed: <span class="blue-text" id="url-n"></span></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <label class="col-xs-6">Lines Indexed: <span class="blue-text" id="line-n"></span></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <label id="index-label" class="col-xs-6">Processed url:</label>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
    <div class="centered">
        <p class="blue-text" id="current-url"></p>
    </div>
</div>
</body>

<script>

    $(document).ready(function () {
        console.log(${indexStart});
        if (${indexStart}) {
            getIndexationStatus();
        } else {
            $(".index-info").hide();
        }
    });

    function getIndexationStatus() {
        var indexStatus;

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/index/ajax",
            dataType: 'json',
            success: function (data) {
                console.log(data);
                indexStatus = data.status;
                if (indexStatus !== "DONE") {
                    $("#url-n").text(data.urlN);
                    $("#line-n").text(data.lineN);
                    $("#current-url").text(data.currentUrl);
                }
                getNextIndex(indexStatus);
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });
    }

    function getNextIndex(indexStatus) {
        console.log(indexStatus);
        if (indexStatus === 'PROCEED') {
            setTimeout(function () {
                getIndexationStatus()
            }, 500);
        } else if (indexStatus === 'DONE'){
            $("#current-url").text("Indexation Done!");
        } else {
            $("#current-url").text("");
        }
    }

</script>

</html>