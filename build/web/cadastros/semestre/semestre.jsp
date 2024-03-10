<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>


<!-- Page Heading -->

<div class="row">
    <div class="col-12">
        <div class="page-title-box">
            <h4 class="page-title float-left">Semestres</h4>

            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li class="breadcrumb-item"><a href="#">Cadastros</a></li>
                <!-- <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/EstadoListar">Estados</a></li>-->
                <li class="breadcrumb-item active">Semestres</li>
            </ol>
                
                <div class="clearflix"></div>
        </div>
    </div>
</div>
         
<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h4 class="m-0 font-weight-bold text-primary">Semestres</h4>
        </div>
        
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped table-bordered basic-datatable" id="datatable" width="98%" cellspacing="0">
                    <thead>
                        <tr>
                            <th align="left">ID</th>
                            <th align="left">Semestre</th>
                            <th align="right"></th>
                            <th align="right"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="semestre" items="${semestres}">
                            <tr>
                               <td align="left">${semestre.idSemestre}</td>
                               <td align="left">${semestre.numSemestre}</td>
                                <td align="center">
                                   <a href="#" id="deletar" title="Excluir"
                                       onclick="deletar(${semestre.idSemestre}, '${semestre.situacao == 'A' ? 'inativar': 'ativar'}')">
                                        <button class="btn btn-group-lg
                                                <c:out value="${semestre.situacao == 'A' ? 'btn-danger': 'btn-success'}"/>">
                                            <c:out value="${semestre.situacao =='A'?'Inativar': 'Ativar'}"/>
                                        </button>
                                    </a> 
                               </td>
                               <td align="center">
                                   <a href="${pageContext.request.contextPath}/SemestreCarregar?idSemestre=${semestre.idSemestre}">
                                       <button class="btn btn-group-lg btn-warning"/>Alterar</button>
                                   </a>                     
                               </td>
                            </tr>
                        </c:forEach>
                    </tbody>
               </table>
            </div> 
        </div>
    </div>
<div align="center">
    <a href="${pageContext.request.contextPath}/SemestreNovo">Novo</a>
    <a href="index.jsp">Voltar � P�gina Inicial</a>
</div>
<script>
        $(document).ready(function(){
            console.log('entrei ready');
            $('#datatable').DataTable({
                "oLanguage": {
                    "sProcessing": "Processando...",
                    "sLengthMenu": "Mostrar _MENU_ resgistros",
                    "sZeroRecords": "Nenhum registro encontrado.",
                    "sInfo": "Mostrando de _START_ at� _END_ de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando de 0 at� 0 de 0 registros",
                    "sInfoFiltered": "",
                    "sInfoFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "oPaginate": {
                        "sFirst": "Primeiro",
                        "sPrevious": "Anterior",
                        "sNext": "Seguinte",
                        "sLast": "�ltimo"
                    }
                }
            });
        }); 
        
        function deletar(codigo, acao) {
        var id = codigo;
        var mensagemSucesso = (acao === 'inativar') ? 'Semestre inativado com sucesso!' : 'Semestre ativado com sucesso!';
            var mensagemErro = (acao === 'inativar') ? 'N�o foi poss�vel inativar o semestre!' : 'N�o foi poss�vel ativar o semestre!';
        console.log(codigo);
        Swal.fire({
            title: 'Voc� tem certeza?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            confirmButtonText: 'Sim!',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: 'post',
                    url: '${pageContext.request.contextPath}/SemestreExcluir',
                    data: {
                        idSemestre: id
                    },
                    success:
                        function(data) {
                            if (data == 1){
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Sucesso',
                                    text: mensagemSucesso,
                                    showConfirmButton: false,
                                    timer: 2000
                                })
                            } else {
                                Swal.fire({
                                    position: 'top-end',
                                    icon: 'error',
                                    title: 'Erro',
                                    text: mensagemErro,
                                    showConfirmButton: false,
                                    timer: 2000
                                });
                            }
                            setTimeout(() => {window.location.href  = "${pageContext.request.contextPath}/SemestreListar"}, 2000);
                        },
                    error:
                        function(data){
                            setTimeout(() => {window.location.href =  "${pageContext.request.contextPath}/SemestreListar"}, 2000);
                        }
                });
            };
        });
    };
        </script>
        
        <%@include file="/footer.jsp" %>

