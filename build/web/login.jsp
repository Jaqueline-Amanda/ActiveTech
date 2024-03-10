<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page ="header.jsp"/>
 <link rel="stylesheet" href="/css/estilo-tela-login.css">


        <!--img estava -->
    
    <div class="container">
       
        <div class="card">
           <div class="container-form">
               <div class="logo">
                <img src="${pageContext.request.contextPath}/img/logo.png" alt="logo Active Tec" class="imagem-logo">
                </div>
                <form>
                     <div class="container-label">Login<span></span></div>
                    
                    <label for="login">Login</label>
                     <input type="text" name = "login" id="login" required="" placeholder="Coloque seu login"
                        onblur="BuscarUsuarioPorNome()"/>

                    <label for="senha">Senha</label>
                    <input type="password" required="" name="senha"  id="senha" placeholder="Coloque sua senha">
                    <label>Tipo</label>
                      <select name="tipo" id="tipo" tabindex="3" class="tipo">
                        <option value="" >Tipo</option>
                        </select>
                    
                    <div class="botao"><button id="submit">Entrar</button></div>
                </form>
            </div>
        </div>
<jsp:include page="footer.jsp"/>
    </div>
 
<script>
    importStyle();
    $(document).ready(function(){
        console.log("entrei função");
        $("#submit").on("click", function(){
            console.log("entrei click submit");
            if($('#login').val() === ""){
                $("#login").focus();
                $("#erro").html("<div> Por favor, preencher o campo login. </div>").show();
                tempo();
                return false;
            }
            if($('#senha').val() === ""){
                $("#senha").focus();
                $("#erro").html("<div> Por favor, preencher o campo senha. </div>").show();
                tempo();
                return false;
            }
            $("#submit").prop("disabled", true);
            $("#submit").html('<i class="fa fa-spinner" aria-hidden="true"</i> Aguarde...');
            $.ajax({
                type: 'post',
                url: 'UsuarioLogar',
                data:{
                    acao: "login",
                    login: $('#login').val(),
                    senha: $('#senha').val(),
                    tipo: $('#tipo').val()
 
                },
                success:
                        function(data){
                            if(data == 'ok'){
                                window.location.href="${pageContext.request.contextPath}/home.jsp";
                            }else{
                              $('#submit').removeAttr('disabled');
                              $('#submit').html('Entrar');
                              $("#wrapper_error").html("<div class='alert alert-danger'>Login ou senha incorreto.</div>").show();
                               tempo();
                        }
                    },
                error:
                    function(data){
                        RefreshTable();
                    }
            });
           
           });
           
           function tempo(){
            setTimeout(function(){
                $("#wrapper_error").hide();
            }, 3000); // 3 segundos
        }
        });
        function BuscarUsuarioPorNome()
        {
            console.log("entrei");
            usuario = '';
            console.log("entrei na function");
            $('#tipo').empty();
            loginUsuario = $('#login').val();
            console.log(loginUsuario);
            if(loginUsuario !== 'null')
            {
                console.log("vai rodar o ajax");
                //console.log(idEst);
                url = "UsuarioBuscarPorLogin?loginusuario="+loginUsuario;
                //console.log(url);
                console.log("entrei de novo 2vez");
                $.getJSON(url, function(result){
                    //alert(result);
                    $.each(result, function(index, value){
                    console.log("entrei de novo 2vez");

                            $('#tipo').append(
                                      '<option id="usuario_' + value.idUsuario
                                        +'"value="' + value.tipo + '">'
                                        +value.tipo + '</option>'
                                       );
                            if(usuario !== ''){
                                $('#usuario_'+usuario).prop({selected: true});
                            }else{
                                $('#usuario_').prop({selected: true});
                            }
                        });
                        console.log("montou o select");
                    }
                    ).fail(function(obj, textStatus, error){
                        alert('Erro do servidor: '+textStatus + ','+error);
                    });
                }
            }
            function importStyle() {
                console.log("TESTE")
                linkElement = document.createElement("link");
                linkElement.rel = "stylesheet";
                linkElement.type = "text/css";
                linkElement.href = "${pageContext.request.contextPath}/css/estilo-tela-login.css";
                document.head.appendChild(linkElement);
            }
 </script>
