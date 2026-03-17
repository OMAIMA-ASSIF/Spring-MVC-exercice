# Rapport d'Exercice : Spring MVC & Spring Security

Ce projet illustre l'implémentation d'une application web de gestion de produits en utilisant le framework **Spring Boot**, avec **Spring MVC** pour la couche web, **Thymeleaf** pour le moteur de template, et **Spring Security** pour la gestion de l'authentification et de l'autorisation.

---

## 📸 Étapes de Réalisation (Captures d'écran)

*Ici, vous pouvez insérer vos captures d'écran pour illustrer les étapes suivantes :*

1.  **Affichage de la liste des produits**
    ![Liste des produits](votre_chemin_vers_image1.png)
2.  **Formulaire d'ajout d'un nouveau produit**
    ![Ajout produit](votre_chemin_vers_image2.png)
3.  **Validation des formulaires (Messages d'erreur)**
    ![Validation](votre_chemin_vers_image3.png)
4.  **Authentification (Page de Login)**
    ![Login](votre_chemin_vers_image4.png)
5.  **Gestion des accès (403 Not Authorized)**
    ![Non Autorisé](votre_chemin_vers_image5.png)

---

## 🛠️ Concepts Techniques Clés

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

## 🚀 Conclusion

Ce projet permet de bien comprendre le cycle de développement d'une application Java Enterprise avec Spring Boot, en mettant l'accent sur la séparation des couches (MVC), la validation des données et la sécurisation critique des accès utilisateur.
