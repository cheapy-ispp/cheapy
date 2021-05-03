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


<cheapy:layout pageName="singUpClient">
<div class="text-center">
    <h2 class="titulo" style="font-family: 'Lobster'; text-align:center; font-size:200%; padding:10px">
        <fmt:message key="new"/><fmt:message key="client"/>
    </h2>
</div>
	<form:form modelAttribute="cliente" class="form-horizontal"
		id="add-client-form">
		<div class="form-group has-feedback">
			<cheapy:inputField label="Nombre" placeholder="Ponga aqui su nombre"
				name="name" />
			<cheapy:inputField label="Direccion" placeholder="Ponga aqui su dirección"
				name="address" />			
			<cheapy:timeField label="Hora de apertura" placeholder="Ponga aqui su hora de apertura (formato HH:mm)"
				name="init" />
			<cheapy:timeField label="Hora de cierre" placeholder="Ponga aqui su hora de cierre (formato HH:mm)"
				name="finish" />
			<cheapy:inputField label="Teléfono" placeholder="Ponga aqui el teléfono del local"
				name="telephone" />
			<cheapy:inputField label="Descripción" placeholder="Ponga aqui su descripción"
				name="description" />
			<cheapy:inputField label="Email" placeholder="Ponga aqui su email"
				name="email" />
			<cheapy:inputField label="Tipo de comida" placeholder="Indique que tipo de comida sirve su negocio"
				name="food" />
			<cheapy:inputField label="Nombre de usuario" placeholder="Ponga aqui su nombre de usuario"
				name="usuar.username" />
			<cheapy:passwordField label="Contraseña" placeholder="Ponga aqui su contraseña"
				name="usuar.password" />
			<cheapy:selectMunicipio label="Municipio" name="municipio" size="1" items="${municipios}" ></cheapy:selectMunicipio>
			<form:hidden path="expiration"/>
			<div class="text-center">
	        	<label id="terminos"  class="">Acepto los <a href="/termAndCondition"  target="_blank">términos y condiciones</a></label>
	        	<input id="terminos"   name="terminos" type="checkbox" required="required">
			</div>	
			<div class="text-center">
				<input type="submit" class="fadeIn fourth"  value="Registrarse">
			</div>
		</div>
	</form:form>

</cheapy:layout>

