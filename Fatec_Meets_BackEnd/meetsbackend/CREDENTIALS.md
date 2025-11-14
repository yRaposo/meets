# 🔐 Credenciais de Teste - Atualizado

## ✅ Emails Simplificados

Todos os emails foram simplificados de `nome.sobrenome@fatec.sp.gov.br` para `nome@fatec.com`

## 🔑 Credenciais de Login

### Senha Única para Todos
**Senha:** `senha123`

### Usuários Disponíveis

| Nome | Email | Senha |
|------|-------|-------|
| João Silva | `joao@fatec.com` | senha123 |
| Maria Santos | `maria@fatec.com` | senha123 |
| Pedro Lima | `pedro@fatec.com` | senha123 |
| Ana Costa | `ana@fatec.com` | senha123 |
| Carlos Mendes | `carlos@fatec.com` | senha123 |
| Julia Fernandes | `julia@fatec.com` | senha123 |
| Lucas Almeida | `lucas@fatec.com` | senha123 |
| Fernanda Rocha | `fernanda@fatec.com` | senha123 |
| Rafael Santos | `rafael@fatec.com` | senha123 |
| Beatriz Lima | `beatriz@fatec.com` | senha123 |

## 🚀 Como Testar

### Login no App

1. **Abra a tela de Login**
2. **Digite:**
   - Email: `joao@fatec.com`
   - Senha: `senha123`
3. **Clique em "Entrar"**
4. ✅ Deve funcionar perfeitamente!

### Criar Nova Conta

Você pode criar uma conta com qualquer email no formato `seunome@fatec.com`

## 🐛 Solução do Erro 401

### Problema Anterior
- ❌ Hash BCrypt antigo não funcionava
- ❌ Emails muito longos: `joao.silva@fatec.sp.gov.br`

### Solução Aplicada
- ✅ Novo hash BCrypt válido para senha `senha123`
- ✅ Emails simplificados: `joao@fatec.com`
- ✅ Mais fácil de lembrar e digitar

## 🔧 Hash BCrypt

O hash usado para a senha `senha123`:
```
$2a$10$8K1p/o7UBP0wKz5/9oe0aOSvvqUn4Cqq5Y5b0NjF.kPQq0j2J6JmW
```

## 📝 Exemplos de Uso

### Frontend (React Native)

```javascript
// LoginScreen.js
const handleLogin = async () => {
    const result = await login('joao@fatec.com', 'senha123');
    if (result.success) {
        console.log('Login bem-sucedido!');
    }
};
```

### Teste via cURL

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@fatec.com",
    "senha": "senha123"
  }'
```

### Resposta Esperada

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "usuario": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@fatec.com"
  }
}
```

## ⚠️ Importante

### Reinicie o Backend

Após as alterações, **reinicie a aplicação backend** para recriar o banco com as novas credenciais:

```powershell
cd Fatec_Meets_BackEnd\meetsbackend
.\mvnw.cmd spring-boot:run
```

### Verificar Logs

Ao iniciar, você deve ver:
```
📦 Iniciando carregamento de dados de teste...
👥 Criando usuários...
✅ 10 usuários criados
📧 Todos os emails simplificados para: nome@fatec.com
🔑 Senha padrão para todos: senha123
```

## 🎯 Checklist

- [ ] Backend reiniciado
- [ ] Logs mostram emails simplificados
- [ ] Testou login com `joao@fatec.com` e `senha123`
- [ ] Login funcionou sem erro 401
- [ ] Atualizar documentações antigas

## 📚 Documentação Atualizada

Os seguintes arquivos foram atualizados:
- ✅ `LoadDatabase.java` - Emails e senhas
- ✅ `CREDENTIALS.md` - Este arquivo com novas credenciais

---

**Atualizado em:** 08/11/2025  
**Motivo:** Simplificação de emails e correção do erro 401
