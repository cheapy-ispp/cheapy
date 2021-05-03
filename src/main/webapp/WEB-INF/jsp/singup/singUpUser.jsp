<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


<cheapy:layout pageName="singUpUser">
<div class="text-center">
    <h2 class="titulo" style="font-family: 'Lobster'; text-align:center; font-size:200%;  padding:10px">
        <fmt:message key="new"/><fmt:message key="usuario"/>
    </h2>
</div>
	<form:form modelAttribute="usuario" class="form-horizontal"
		id="add-user-form">
		<div class="form-group has-feedback">
			<cheapy:inputField label="Nombre" placeholder="Ponga aqui su nombre"
				name="nombre" />
			<cheapy:inputField label="Apellidos" placeholder="Ponga aqui sus apellidos"
				name="apellidos" />
			
			<cheapy:inputField label="Email" placeholder="Ponga aqui su email"
				name="email" />
			<cheapy:inputField label="Nombre de usuario" placeholder="Ponga aqui su nombre de usuario"
				name="usuar.username" />
			<cheapy:passwordField label="Contraseña" placeholder="Ponga aqui su contraseña" 
				name="usuar.password" />
			
	        <div class="text-center">
	        <label id="terminos"  class="">Acepto los <a href="/termAndCondition"  target="_blank" >términos y condiciones</a></label>
	        <input id="terminos"   name="terminos" type="checkbox" required="required">
			</div>
		</div>
			<div class="text-center">
				<input type="submit" class="fadeIn fourth"  value="Registrarse">
			</div>
	</form:form>

</cheapy:layout>

