<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<style>
.btn-social {
  padding: 12px;
  border: none;
  border-radius: 4px;
  margin: 5px 0;
  opacity: 0.85;
  display: inline-block;
  font-size: 17px;
  line-height: 20px;
  text-decoration: none
}

.btn-social:hover {
  opacity: 1;
}

.google {
  background-color: #dd4b39;
  color: white;
}

p:before, p:after {
     content: "";
     flex: 1 1;
     border-bottom: 2px solid #000;
     margin: auto;
}

p {
            display: flex;
            flex-direction: row;
        }
        
.fa-login {
	font-size: 30px;
    text-align: center;
    text-decoration: none!important;
    margin: 5px 2px;
    border-radius: 50%;
    
    display: inline-block;
    font: normal normal normal 14px/1 FontAwesome;
    font-size: inherit;
    text-rendering: auto;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
        
</style>

<cheapy:layout pageName="login">
  
	<div class="wrapper fadeInDown">
	  <div id="formContent">
	    <!-- Tabs Titles -->
		
	    <!-- Icon -->
	    <div class="fadeIn first">
	      <img src="/resources/images/Logo Cheapy.png" id="icon" />
	      <c:if test= "${not empty param}" > 
	      	<p class="text-danger"> El usuario y/o la contraseña son incorrectos </p> 
	      
	      </c:if>
	    </div>
	    
  		<a href="/oauth2/authorization/google" class="google btn-social"><i class="fa-login fa-google fa-fw">
          </i> Login with Google
        </a>
        
        <p>o</p>
        
	    <!-- Login Form -->
	    <form class='form-signin' action="/login" method='POST'>
	      <input type="text" id="username" class="fadeIn second" name="username" placeholder="Usuario" required autofocus>
	      <input type="password" id="password" class="fadeIn third" name="password" placeholder="Contraseña" required>
	      <sec:csrfInput />  
	      <div style="text-align: center;">
		  	<input type="submit" class="fadeIn fourth"  value="Iniciar sesión">
		  </div>
		  
	    </form>
	    
	    <a class="underlineHover fadeIn fourth" href="/forgottenPassword"><fmt:message key="forgottenPassword"/></a>

		<button class="fadeIn fourth" id='volver' type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster';">Volver</button>
	  </div>
	</div>    

</cheapy:layout>
