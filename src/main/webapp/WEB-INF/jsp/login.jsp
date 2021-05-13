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

	    <!-- Login Form -->
	    <form class='form-signin' action="/login" method='POST'>
	      <input type="text" id="username" class="fadeIn second" name="username" placeholder="Usuario" required autofocus>
	      <input type="password" id="password" class="fadeIn third" name="password" placeholder="Contraseña" required>
	      <sec:csrfInput />  
	      <div style="text-align: center;">
		  	<input type="submit" class="fadeIn fourth"  value="Iniciar sesión">
		  </div>
	    </form>
	
	    <!-- Remind Passowrd 
	    <div id="formFooter">
	      <a class="underlineHover" href="#">¿Olvidó su contraseña?</a>
	    </div>
	-->
	  </div>
	</div>    

</cheapy:layout>
