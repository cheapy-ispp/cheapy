<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<cheapy:layout pageName="payment">
		<div class="text-center">
			<p class="infoPago"><em>La compra no se completó o se canceló. Puede volver a intentarlo pinchando en el siguiente botón: </em></p>
			<div class="btn-home">
                 <button type="button" role="link" onclick="window.location='/pay'" style="font-family: 'Lobster'; font-size: 20px;margin:5px;" class="btn-block">
                 <span class="fa fa-paypal" aria-hidden="true" style="padding: 5px"> </span>
                 <fmt:message key="pay"/> </button>
            </div>
		
		</div>
</cheapy:layout>