<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<cheapy:layout pageName="payment">
	<center>
	
		<c:if test="${payCham==true}">
			<h2>${fn:escapeXml(order.price)}${fn:escapeXml(order.currency)}</h2>
			<h2>${fn:escapeXml(order.description)}</h2>		
		</c:if>
		
		<form:form commandName="order" modelAttribute="order" method="post" action="/pay">
			<div>
				<form:input type="hidden" path="price" />
				<form:input type="hidden" path="currency" />
				<form:input type="hidden" path="method" />
				<form:input type="hidden" path="intent" />
				<form:input type="hidden" path="description" />

				<div class="form-group">
					<div class="infoPago">
						<p> Precio de la suscripción: </p>
						<p><br><strong>${fn:escapeXml(order.price)} €</strong></p>
						<p><br><em>${fn:escapeXml(order.description)} </em></p>
					</div>
					<button class="boton" value="Pagar" type="submit">
						<spring:url value="/resources/images/paypal240.png" htmlEscape="true" var="paypal240"/>
    					<img class="img-responsive" src="${fn:escapeXml(paypal240)}"/>
					</button>
					<div class="infoPago">
						<p><em> (Puede pagar usando Paypal o su tarjeta de crédito o débito)</em></p>
						
					</div>
				</div>
			</div>
		</form:form>
	</center>
</cheapy:layout>