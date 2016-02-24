<html>
    <head>
        <meta charset="UTF-8" />
        <link rel="stylesheet" href="/styles/style1.css" type="text/css" />
        <link rel="stylesheet" href="/styles/animate-custom.css" type="text/css" />
        <title>Login</title>
    </head>
    <body>
        <div class="container">
          <div class="clr"></div>
            <section>				
                <div id="container_demo" >
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form  name="LoginForm" action="/login" autocomplete="on" method="POST">
                                <h1>Log in</h1>
                                <p>
                                    <label for="username" class="uname" data-icon="u" > Your email address </label>
                                    <input id="username" name="email" required="required" type="text" placeholder="myusername or mymail@mail.com"/>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p"> Your password </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO" /> 
                                </p>
                                <p class="login button">
                                	<% 
                                		if( request.getAttribute("errorMsg") != null )
                                		{
                                			out.println("<label>" + request.getAttribute("errorMsg") + "</label>");
                                		}
                                	%> 
                                    <input type="submit" value="Login" /> 
								</p>
                                <p class="change_link">
									Not a member yet ?
									<a href="#toregister" class="to_register">Join us</a>
								</p>
                            </form>
                        </div>

                        <div id="register" class="animate form">
                            <form name="SignUp" action="/signUp" autocomplete="on" method="POST"> 
                                <h1> Sign up </h1> 
                                <p> 
                                    <label for="usernamesignup" class="uname" data-icon="u">Your username</label>
                                    <input id="usernamesignup" name="userName" required="required" type="text" placeholder="mysuperusername690" />
                                </p>
                                <p> 
                                    <label for="emailsignup" class="youmail" data-icon="e" > Your email address</label>
                                    <input id="emailsignup" name="email" required="required" type="email" placeholder="mysupermail@mail.com"/> 
                                </p>
                                <p> 
                                    <label for="passwordsignup" class="youpasswd" data-icon="p">Your password </label>
                                    <input id="passwordsignup" name="password" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p> 
                                    <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your password </label>
                                    <input id="passwordsignup_confirm" name="passwordConfirm" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p class="signin button"> 
									<input type="submit" value="Sign up"/>
								</p>
                                <p class="change_link">
									Already a member ?
									<a href="#tologin" class="to_register"> Go and log in </a>
								</p>
                            </form>
                        </div>
						
                    </div>
                </div>  
            </section>
        </div>
    </body>
   	<script type="text/javascript">
   	onLoad();
   	
   	function onLoad()
   	{
   		document.getElementById("username").focus();
   	}
   	</script>
</html>
