<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>An error occurred</h1>
    <p>We're sorry, but something went wrong.</p>
    <p>Error details: ${exception.message}</p>
    <a href="${pageContext.request.contextPath}/home">Go to Home Page</a>
</body>
