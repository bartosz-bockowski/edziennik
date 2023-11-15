pod linkiem "/" jest akcja kontrolera HomeController ktora przenosi do widoku thymeleaf src/main/resources/templates/home.html
w tym widoku używam tagów sec:authorize, które nie działają, strona działa tak jakby ich nie było
konkretniej pokazują się dwa divy, jeden z nich ma warunek isAnonymous a drugi isAuthenticated więc nie powinny się pokazywać dwa na raz w żadnej sytuacji
a w przypadku braku logowania pokazują się oba
inne tagi na przyklad 'hasRole' tez pokazuja diva ktory jest przez nie oznaczony nawet kiedy uzytkownik nie jest zalogowany
albo jest hasRole z parametrem z losowa nazwa roli na przyklad hasRole('asdjasfghaweiosgsdv')
