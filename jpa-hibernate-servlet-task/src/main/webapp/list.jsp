<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <title>List</title>
</head>
<body>
<div class="row">
    <div class="col-md-8 col-md-offset-2">
        <h2 class="page-header">User list</h2>

        <table class="table table-striped">
            <tr>
                <th>#</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Email</th>
                <th>Birthday</th>
                <th></th>
            </tr>
            <c:forEach items="${userList}" var="user" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.credentials.email}</td>
                    <td>${user.birthday}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

</body>
</html>
