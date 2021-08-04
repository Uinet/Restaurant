<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css">
        <link type="text/css" rel="stylesheet" href="/css/style.css">
    </head>
    <body class="text-center">
        <form method="post" class="form-login">
            <h1>Login</h1>
            <#if error>
                <div class="alert alert-danger" role="alert">
                    Incorrect login or password!
                </div>
            </#if>
            <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
            <label for="username">Username</label>
            <input type="text" class="login-input" id="username" name="username" placeholder="Login"></input>
            <label for="password">Password</label>
            <input type="password" class="login-input" id="password" name="password" placeholder="Password"></input>
            <button type="submit" class="submit-button">Login</button>
        </form>
    </body>
</html>