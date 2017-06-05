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

    <title>Create</title>
</head>
<body>
<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <h2 class="page-header">Create new account</h2>
        <form>
            <div class="form-group">
                <label for="firstName">First name</label>
                <input type="text" class="form-control" id="firstName" placeholder="Johnny">
            </div>
            <div class="form-group">
                <label for="lastName">Last name</label>
                <input type="text" class="form-control" id="lastName" placeholder="Resko">
            </div>
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" class="form-control" id="email" placeholder="jresko@gmail.com">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" placeholder="****">
            </div>
            <div class="form-group">
                <label for="birthday">Birthday</label>
                <input type="date" class="form-control" id="birthday" placeholder="08/19/1969">
            </div>

            <button type="Save" class="btn btn-default">Submit</button>
        </form>
    </div>
</div>

</body>
</html>