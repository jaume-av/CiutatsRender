Canviar Rutes a plantillaCiutats:

```html
   <a th:href="@{'../fitxersWeb/' + ${ciutat.nom} + '.html'}" th:text="${ciutat.nom}"style="color: #388e3c; text-decoration: none;"></a>

   <a th:href="@{'/fitxersWeb/' + ${ciutat.nom} + '.html'}" th:text="${ciutat.nom}"style="color: #388e3c; text-decoration: none;"></a>
```
a: /fitxersWeb/

3.-
