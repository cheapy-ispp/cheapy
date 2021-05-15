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

<cheapy:layout pageName="contrasenaOlvidada">
  
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

	    <!-- ContrasenaOlvidada Form -->
	    <form class='form-signin' action="/forgottenPassword" method='POST'>
	      <input type="text" id="username" class="fadeIn second" name="username" placeholder="Usuario" required autofocus>
	      <input type="text" id="preguntaSegura1" class="fadeIn third" name="preguntaSegura1" placeholder="Pregunta segura 1: ¿Cuál es su plato de comida favorito?" required>
	      <input type="text" id="preguntaSegura2" class="fadeIn third" name="preguntaSegura2" placeholder="Pregunta segura 2: ¿Cuál es su ciudad de nacimiento?" required>
	      <input type="password" id="nuevaContrasena" class="fadeIn third" name="nuevaContrasena"" placeholder="Nueva Contrasena" required autofocus>
	      <sec:csrfInput />  
	      <div style="text-align: center;">
		  	<input type="submit" class="fadeIn fourth"  value="Iniciar sesión">
		  </div>
	    </form>
	    <button id='volver' type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster';">Volver</button>
	  </div>
	</div>    

</cheapy:layout>
