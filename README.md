# Rapport d'Exercice : Spring MVC & Spring Security

Ce projet illustre l'implémentation d'une application web de gestion de produits en utilisant le framework **Spring Boot**, avec **Spring MVC** pour la couche web, **Thymeleaf** pour le moteur de template, et **Spring Security** pour la gestion de l'authentification et de l'autorisation.

---

##  Étapes de Réalisation (Captures d'écran)

*Ici, vous pouvez insérer vos captures d'écran pour illustrer les étapes suivantes :*

<img width="475" height="206" alt="products-before" src="https://github.com/user-attachments/assets/5bfdc9ad-6c9c-424a-a9a8-5c8119853ebe" />
<img width="1350" height="271" alt="alerte confirmation" src="https://github.com/user-attachments/assets/ae6b5ee8-c48e-49d3-a1c7-3afd6d025501" />
<img width="1350" height="390" alt="addForm" src="https://github.com/user-attachments/assets/8d82f140-07e7-4ffd-8f09-47082d165a6a" />
<img width="1356" height="387" alt="user-nom" src="https://github.com/user-attachments/assets/bc7a68e0-72c2-4294-b8bb-e3cac1cdd7de" />
<img width="1362" height="281" alt="notauthorized" src="https://github.com/user-attachments/assets/84da6cb7-0f2d-4767-ab3f-5c4dee10c0c2" />
<img width="1021" height="295" alt="tokencsrf" src="https://github.com/user-attachments/assets/ca0b93a6-a62b-4ed8-b096-ec8996abc903" />
<img width="1160" height="347" alt="login" src="https://github.com/user-attachments/assets/69d62ae0-2633-41a3-aed0-fe7275e3add7" />
<img width="1357" height="575" alt="final" src="https://github.com/user-attachments/assets/026bf8c8-ae88-4799-a7ee-442c408b0fb5" />


---

## Concepts Techniques Clés

### 1. Moteur de Template : Thymeleaf

Thymeleaf est utilisé pour générer dynamiquement le contenu HTML côté serveur. Voici les syntaxes principales utilisées :

*   **`th:href="@{/path}"`** : Utilisé pour définir des liens dynamiques. Thymeleaf gère automatiquement le contexte de l'application.
    ```html
    <a th:href="@{/admin/newProduct}">Add new</a>
    ```
*   **`th:each="p:${productsList}"`** : Permet de parcourir une collection de données envoyée depuis le contrôleur.
    ```html
    <tr th:each="p:${productsList}">
    ```
*   **`th:text="${p.id}"`** : Remplace le contenu d'une balise par la valeur d'une expression évaluée.
    ```html
    <td th:text="${p.id}"></td>
    ```

### 2. Validation des Formulaires

Pour garantir l'intégrité des données, nous utilisons l'annotation **`@Valid`** associée aux contraintes de validation dans l'entité.

*   **Dans l'entité `Product`** :
    ```java
    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;
    ```
*   **Dans le contrôleur** :
    L'annotation `@Valid` déclenche la validation de l'objet. Si des erreurs surviennent, elles sont stockées dans l'objet **`BindingResult`**.
    ```java
    @PostMapping("/admin/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "new-product"; // Retourne au formulaire en cas d'erreur
        }
        productRepository.save(product);
        return "redirect:/user/index";
    }
    ```

### 3. Opérations de Suppression Sécurisées

Pour la suppression de ressources, il est fortement déconseillé d'utiliser la méthode `GET` afin d'éviter les suppressions accidentelles ou malveillantes (via des robots de recherche par exemple). Nous utilisons donc **`@PostMapping`**.

```java
@PostMapping("/admin/delete")
public String delete(@RequestParam(name = "id") Long id) {
    productRepository.deleteById(id);
    return "redirect:/user/index";
}
```

### 4. Sécurité avec Spring Security

La sécurité a été personnalisée pour gérer l'authentification et l'autorisation de manière robuste.

*   **Authentification et Rôles** : Nous avons défini des utilisateurs avec des rôles spécifiques (`USER`, `ADMIN`).
*   **Hashage des Mots de Passe** : Utilisation de l'algorithme **BCrypt** via `BCryptPasswordEncoder` pour stocker les mots de passe de manière sécurisée.
    ```java
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    ```
*   **Mode "Stateful" et protection CSRF** : Par défaut, Spring Security active la protection contre les attaques **CSRF** (Cross-Site Request Forgery). Un **token caché** (`_csrf`) est automatiquement ajouté aux formulaires pour valider la légitimité de la requête.
*   **Protection des Ressources** :
    Il existe deux approches principales pour protéger les ressources :
    1.  **Via `authorizeHttpRequests`** (Configuration Gloable) :
        ```java
        http.authorizeHttpRequests(ar -> ar.requestMatchers("/admin/**").hasRole("ADMIN"));
        ```
    2.  **Via `@PreAuthorize`** (Sur les méthodes) :
        Nécessite l'activation avec `@EnableGlobalMethodSecurity(prePostEnabled=true)` (ou `@EnableMethodSecurity` dans les versions récentes).

---

## Conclusion

Ce projet permet de bien comprendre le cycle de développement d'une application Java Enterprise avec Spring Boot, en mettant l'accent sur la séparation des couches (MVC), la validation des données et la sécurisation critique des accès utilisateur.
