<%@ include file="../fragments/taglibs.jsp"%>

<head>
    <meta charset="utf-8">
    <title>SPD Ukraine Test Program</title>

    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
    <link href="${bootstrapCss}" rel="stylesheet" />

    <spring:url value="/resources/core/css/app.css" var="appCss" />
    <link href="${appCss}" rel="stylesheet" />

    <spring:url value="/resources/core/js/jquery-1.10.1.min.js" var="jquery" />
    <script src="${jquery}"></script>
</head>