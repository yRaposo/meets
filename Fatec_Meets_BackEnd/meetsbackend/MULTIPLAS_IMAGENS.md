# 📸 Sistema de Múltiplas Imagens - Implementado

## ✅ O que foi implementado

### **1. Nova Entidade `Imagem`**
```java
@Entity
public class Imagem {
    private Long id;
    private String url;           // URL da imagem
    private String descricao;     // Descrição/alt text
    private Postagem postagem;    // Post ao qual pertence
    private Integer ordem;        // Ordem de exibição (0, 1, 2...)
}
```

### **2. Relacionamento OneToMany em `Postagem`**
```java
@OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
@OrderBy("ordem ASC")
private List<Imagem> imagens = new ArrayList<>();
```

### **3. Evento usa as imagens do Post vinculado**
```java
// No Evento.java
public List<Imagem> getImagens() {
    return postagem != null ? postagem.getImagens() : new ArrayList<>();
}
```

---

## 🎯 Como Funciona

### **Regra Principal:**
- ✅ **Posts** podem ter **múltiplas imagens** (0 a N imagens)
- ✅ **Eventos** compartilham as **mesmas imagens do post vinculado**
- ✅ Campo `imagemUrl` mantido para compatibilidade (mas deprecated)

### **Vantagens:**
1. 📸 **Carrossel de imagens** nos posts
2. 🔄 **Sem duplicação** - evento usa imagens do post
3. 📊 **Ordem personalizada** - campo `ordem` controla a sequência
4. 📝 **Acessibilidade** - campo `descricao` para alt text

---

## 📊 Estrutura do Banco

### **Tabela `imagem`**
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT | PK auto increment |
| url | VARCHAR(500) | URL da imagem |
| descricao | VARCHAR(200) | Descrição/alt text |
| postagem_id | BIGINT | FK para postagem |
| ordem | INTEGER | Ordem de exibição |

---

## 🔄 Exemplos de Uso

### **1. Criar Post com Múltiplas Imagens**

#### **Backend (Java):**
```java
// Criar o post
Postagem post = new Postagem("Título", "Conteúdo", usuario);
post = postagemRepository.save(post);

// Adicionar imagens
imagemRepository.save(new Imagem("url1.jpg", "Descrição 1", post, 0));
imagemRepository.save(new Imagem("url2.jpg", "Descrição 2", post, 1));
imagemRepository.save(new Imagem("url3.jpg", "Descrição 3", post, 2));
```

#### **API REST:**
```json
POST /api/postagens
{
  "titulo": "Evento Incrível",
  "conteudo": "Descrição do evento",
  "usuarioId": 1,
  "imagens": [
    {
      "url": "https://picsum.photos/800/400?random=1",
      "descricao": "Banner principal",
      "ordem": 0
    },
    {
      "url": "https://picsum.photos/800/400?random=2",
      "descricao": "Espaço do evento",
      "ordem": 1
    }
  ]
}
```

### **2. Buscar Post com Imagens**

#### **Resposta da API:**
```json
{
  "id": 1,
  "titulo": "Hackathon 2025",
  "conteudo": "Descrição do hackathon...",
  "usuario": {...},
  "imagens": [
    {
      "id": 1,
      "url": "https://picsum.photos/800/400?random=10",
      "descricao": "Banner principal",
      "ordem": 0
    },
    {
      "id": 2,
      "url": "https://picsum.photos/800/400?random=20",
      "descricao": "Espaço do evento",
      "ordem": 1
    },
    {
      "id": 3,
      "url": "https://picsum.photos/800/400?random=30",
      "descricao": "Prêmios",
      "ordem": 2
    }
  ],
  "evento": {
    "id": 1,
    "titulo": "Hackathon 2025",
    "dataEvento": "2025-11-15T08:00:00"
  }
}
```

### **3. Buscar Evento (usa imagens do post)**

```json
GET /api/eventos/1

{
  "id": 1,
  "titulo": "Hackathon 2025",
  "dataEvento": "2025-11-15T08:00:00",
  "local": "Campus Fatec",
  "postagem": {
    "id": 1,
    "titulo": "Hackathon 2025",
    "imagens": [
      {
        "id": 1,
        "url": "https://picsum.photos/800/400?random=10",
        "descricao": "Banner principal",
        "ordem": 0
      },
      {
        "id": 2,
        "url": "https://picsum.photos/800/400?random=20",
        "descricao": "Espaço do evento",
        "ordem": 1
      }
    ]
  }
}
```

---

## 📱 Frontend (React Native)

### **Carrossel de Imagens:**

```javascript
import { FlatList, Image, View, Dimensions } from 'react-native';

function PostCarousel({ post }) {
  const renderImage = ({ item }) => (
    <Image
      source={{ uri: item.url }}
      style={{
        width: Dimensions.get('window').width - 40,
        height: 200,
        borderRadius: 10,
      }}
      resizeMode="cover"
    />
  );

  return (
    <FlatList
      data={post.imagens}
      renderItem={renderImage}
      keyExtractor={(item) => item.id.toString()}
      horizontal
      pagingEnabled
      showsHorizontalScrollIndicator={false}
    />
  );
}
```

### **Componente de Post:**

```javascript
function Post({ post }) {
  return (
    <View style={styles.postContainer}>
      <Text style={styles.title}>{post.titulo}</Text>
      <Text style={styles.content}>{post.conteudo}</Text>
      
      {/* Carrossel de imagens */}
      {post.imagens && post.imagens.length > 0 && (
        <PostCarousel post={post} />
      )}
      
      {/* Indicador de quantidade de imagens */}
      {post.imagens && post.imagens.length > 1 && (
        <Text style={styles.imageCount}>
          {post.imagens.length} fotos
        </Text>
      )}
    </View>
  );
}
```

### **Evento (usa imagens do post):**

```javascript
function EventCard({ evento }) {
  // Evento usa as imagens do post vinculado
  const imagens = evento.postagem?.imagens || [];

  return (
    <View style={styles.eventCard}>
      {imagens.length > 0 && (
        <Image
          source={{ uri: imagens[0].url }}
          style={styles.eventBanner}
        />
      )}
      
      <Text style={styles.eventTitle}>{evento.titulo}</Text>
      <Text style={styles.eventDate}>{evento.dataEvento}</Text>
      <Text style={styles.eventLocal}>{evento.local}</Text>
      
      {imagens.length > 1 && (
        <Text style={styles.morePhotos}>
          +{imagens.length - 1} fotos
        </Text>
      )}
    </View>
  );
}
```

---

## 📊 Dados de Teste no LoadDatabase

### **Posts com múltiplas imagens:**
- **Post 2** (Hackathon) - **3 imagens**
- **Post 5** (Palestra Cloud) - **1 imagem**
- **Post 8** (Game Jam) - **2 imagens**
- **Post 10** (Workshop Git) - **1 imagem**

### **Outros posts:**
- Mantêm o campo `imagemUrl` para compatibilidade
- Podem migrar para o novo sistema posteriormente

---

## 🔧 Migração de `imagemUrl` para `imagens`

Se você tem posts antigos com `imagemUrl`, pode criar um script de migração:

```java
@Service
public class ImagemMigrationService {
    
    @Autowired
    private PostagemRepository postagemRepository;
    
    @Autowired
    private ImagemRepository imagemRepository;
    
    @Transactional
    public void migrarImagensAntigas() {
        List<Postagem> posts = postagemRepository.findAll();
        
        for (Postagem post : posts) {
            // Se tem imagemUrl mas não tem imagens na lista
            if (post.getImagemUrl() != null && 
                !post.getImagemUrl().isEmpty() && 
                post.getImagens().isEmpty()) {
                
                // Criar nova imagem na lista
                Imagem imagem = new Imagem(
                    post.getImagemUrl(),
                    "Imagem do post",
                    post,
                    0
                );
                imagemRepository.save(imagem);
                
                log.info("Migrada imagem do post {}", post.getId());
            }
        }
    }
}
```

---

## 🚀 Próximos Passos

1. **Reinicie o backend** para criar a tabela `imagem`
2. **Teste a API** e veja as múltiplas imagens nos posts
3. **Implemente carrossel** no frontend React Native
4. **Teste eventos** e confirme que usam imagens do post
5. **(Opcional)** Migre posts antigos de `imagemUrl` para `imagens`

---

## 📝 Resumo das Mudanças

### **Arquivos Criados:**
- ✅ `Imagem.java` - Nova entidade
- ✅ `ImagemRepository.java` - Repository para imagens

### **Arquivos Modificados:**
- ✅ `Postagem.java` - Adicionado `List<Imagem> imagens`
- ✅ `Evento.java` - Removido `imagemUrl`, adicionado `getImagens()`
- ✅ `LoadDatabase.java` - Exemplos com múltiplas imagens

### **Vantagens:**
- 📸 Suporte a carrossel de imagens
- 🔄 Sem duplicação entre post e evento
- 📊 Controle de ordem das imagens
- 📝 Acessibilidade com descrições
- ✅ Compatibilidade com código antigo (`imagemUrl`)

---

**Data de Implementação:** 08/11/2025  
**Versão:** 2.0 - Sistema de Múltiplas Imagens
