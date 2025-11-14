# 📸 Guia de Imagens - Fatec Meets

## ✅ Campos Adicionados

### **Usuario** - Foto de Perfil
```java
private String fotoPerfil;  // URL da foto de perfil
```

### **Postagem** - Imagem do Post
```java
private String imagemUrl;  // URL da imagem do post
```

### **Evento** - Banner do Evento
```java
private String imagemUrl;  // URL da imagem/banner do evento
```

---

## 🚀 Como Usar

### 1️⃣ **Opção Simples: URLs Diretas**

Use URLs de imagens já hospedadas na internet:

#### Exemplos de URLs válidas:
```
https://i.imgur.com/abc123.jpg
https://exemplo.com/images/foto.png
https://picsum.photos/200/300
https://via.placeholder.com/150
```

#### Criar Usuário com Foto de Perfil:
```json
POST /api/usuarios
{
  "nome": "João Silva",
  "email": "joao@fatec.com",
  "senha": "senha123",
  "bio": "Desenvolvedor Full Stack",
  "fotoPerfil": "https://i.imgur.com/avatar123.jpg"
}
```

#### Criar Post com Imagem:
```json
POST /api/postagens
{
  "titulo": "Meu novo projeto",
  "conteudo": "Confira meu projeto incrível!",
  "usuarioId": 1,
  "imagemUrl": "https://i.imgur.com/projeto456.png"
}
```

#### Criar Evento com Banner:
```json
POST /api/eventos
{
  "titulo": "Hackathon 2025",
  "dataEvento": "2025-12-01T09:00:00",
  "local": "Campus Fatec",
  "criadorId": 1,
  "postagemId": 5,
  "imagemUrl": "https://i.imgur.com/hackathon789.jpg"
}
```

---

## 🌐 Serviços de Hospedagem de Imagens (Gratuitos)

### **1. Imgur** ⭐ RECOMENDADO
- 🔗 https://imgur.com
- ✅ Upload gratuito
- ✅ Não precisa criar conta
- ✅ URLs permanentes
- 📝 Como usar:
  1. Acesse imgur.com
  2. Clique em "New Post"
  3. Faça upload da imagem
  4. Copie o link direto da imagem

### **2. Cloudinary**
- 🔗 https://cloudinary.com
- ✅ 25 GB grátis/mês
- ✅ API completa
- ✅ Redimensionamento automático
- 📝 Requer cadastro

### **3. ImgBB**
- 🔗 https://imgbb.com
- ✅ Upload gratuito
- ✅ Sem registro necessário
- ✅ URLs permanentes

### **4. Postimages**
- 🔗 https://postimages.org
- ✅ Upload gratuito
- ✅ Sem registro
- ✅ Galeria online

### **5. Placeholder (Para Testes)**
- 🔗 https://picsum.photos
- ✅ Imagens aleatórias para testes
- 📝 Exemplo: `https://picsum.photos/200/300`

---

## 📱 Upload de Imagens no React Native

### **Opção 1: Usar Expo ImagePicker + Imgur API**

```javascript
import * as ImagePicker from 'expo-image-picker';

async function pickImage() {
  // Solicitar permissão
  const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();
  
  if (status !== 'granted') {
    alert('Precisamos de permissão para acessar suas fotos!');
    return;
  }

  // Selecionar imagem
  const result = await ImagePicker.launchImageLibraryAsync({
    mediaTypes: ImagePicker.MediaTypeOptions.Images,
    allowsEditing: true,
    aspect: [4, 3],
    quality: 0.7,
  });

  if (!result.canceled) {
    const imageUrl = await uploadToImgur(result.assets[0].uri);
    return imageUrl;
  }
}

async function uploadToImgur(imageUri) {
  const formData = new FormData();
  formData.append('image', {
    uri: imageUri,
    type: 'image/jpeg',
    name: 'photo.jpg',
  });

  const response = await fetch('https://api.imgur.com/3/image', {
    method: 'POST',
    headers: {
      'Authorization': 'Client-ID YOUR_IMGUR_CLIENT_ID',
    },
    body: formData,
  });

  const data = await response.json();
  return data.data.link; // URL da imagem
}
```

### **Opção 2: Usar URL Direta (Mais Simples)**

```javascript
// Componente de perfil
const [fotoPerfil, setFotoPerfil] = useState('');

function handleUpdateProfile() {
  const data = {
    nome: nome,
    email: email,
    bio: bio,
    fotoPerfil: fotoPerfil, // URL da imagem
  };
  
  userService.updateUser(userId, data);
}

// No formulário
<TextInput
  placeholder="URL da foto de perfil"
  value={fotoPerfil}
  onChangeText={setFotoPerfil}
/>
```

---

## 🔄 Atualizar LoadDatabase com Imagens

Adicione URLs de imagens nos dados de teste:

```java
Usuario joao = usuarioRepository.save(new Usuario(
    "João Silva",
    "joao@fatec.com",
    senha,
    "Desenvolvedor Full Stack apaixonado por tecnologia! 🚀"
));
joao.setFotoPerfil("https://i.pravatar.cc/150?img=1");
usuarioRepository.save(joao);

Postagem post1 = new Postagem(
    "Novo Projeto em React",
    "Estou desenvolvendo um aplicativo incrível!",
    joao
);
post1.setImagemUrl("https://picsum.photos/400/300?random=1");
post1 = postagemRepository.save(post1);

Evento evento1 = new Evento(
    "Hackathon 2025",
    LocalDateTime.of(2025, 12, 1, 9, 0),
    "Campus Fatec",
    joao,
    post1
);
evento1.setImagemUrl("https://picsum.photos/800/400?random=10");
eventoRepository.save(evento1);
```

---

## 🎨 Exibir Imagens no React Native

```javascript
import { Image } from 'react-native';

// Foto de perfil
<Image
  source={{ uri: usuario.fotoPerfil || 'https://via.placeholder.com/150' }}
  style={{ width: 50, height: 50, borderRadius: 25 }}
/>

// Imagem do post
{post.imagemUrl && (
  <Image
    source={{ uri: post.imagemUrl }}
    style={{ width: '100%', height: 200, borderRadius: 10 }}
    resizeMode="cover"
  />
)}

// Banner do evento
{evento.imagemUrl && (
  <Image
    source={{ uri: evento.imagemUrl }}
    style={{ width: '100%', height: 150 }}
    resizeMode="cover"
  />
)}
```

---

## ⚠️ Validações Importantes

### Backend (Opcional - adicionar nos controllers):

```java
// Validar URL de imagem
private boolean isValidImageUrl(String url) {
    if (url == null || url.trim().isEmpty()) {
        return true; // URL é opcional
    }
    
    return url.matches("^https?://.*\\.(jpg|jpeg|png|gif|webp)$") ||
           url.matches("^https?://.*");
}
```

### Frontend:

```javascript
// Validar URL antes de enviar
function isValidUrl(url) {
  if (!url) return true; // URL opcional
  
  try {
    new URL(url);
    return true;
  } catch {
    return false;
  }
}
```

---

## 📋 Checklist de Implementação

### Backend:
- ✅ Campos `imagemUrl` e `fotoPerfil` adicionados nos models
- ✅ Getters e setters criados
- [ ] Reiniciar aplicação para criar as novas colunas
- [ ] Atualizar LoadDatabase com URLs de teste
- [ ] (Opcional) Adicionar validações nos controllers

### Frontend:
- [ ] Adicionar campo de URL nos formulários
- [ ] Exibir imagens usando componente `Image`
- [ ] Adicionar placeholder para quando não houver imagem
- [ ] (Opcional) Implementar upload com ImagePicker

---

## 🎯 Exemplos Rápidos de URLs para Teste

### Fotos de Perfil (Avatares):
```
https://i.pravatar.cc/150?img=1
https://i.pravatar.cc/150?img=2
https://ui-avatars.com/api/?name=Joao+Silva&size=150
```

### Imagens de Posts:
```
https://picsum.photos/400/300?random=1
https://picsum.photos/400/300?random=2
https://source.unsplash.com/random/400x300?technology
```

### Banners de Eventos:
```
https://picsum.photos/800/400?random=10
https://source.unsplash.com/random/800x400?event
https://via.placeholder.com/800x400/9C2222/ffffff?text=Hackathon+2025
```

---

## 🚀 Próximos Passos

1. **Reinicie o backend** para criar as colunas no banco
2. **Teste as APIs** com URLs de imagens
3. **Atualize o LoadDatabase** com URLs de exemplo
4. **Implemente no frontend** os campos de imagem
5. **(Futuro)** Implementar upload real de arquivos se necessário

---

**Data de Atualização:** 08/11/2025  
**Versão:** 1.0
