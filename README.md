# CurvedFractals
https://stackedit.io/app#
![enter image description here](https://raw.githubusercontent.com/LudoDm/CurvedFractals/master/res/images/demo.png)
CurvedFractals est un logiciel libre et ouvert, développé dans le cadre du projet d'intégration du DEC en Sciences Informatiques et Mathématiques. 
Ce logiciel, ultime expérience esthétique, prétend humblement permettre de visualiser l’interaction entre deux objets mathématiques, soit les fractales et les variétés riemanniennes (au sens *très* faible du terme: la responsabilité de doter la variété d'une métrique **riemannienne** est laissé à l'utilisateur, ainsi que celle de s'assurer que la paramétrisation qu'il fournit donne effectivement une variété différentielle, mais le résultat s'affiche même si rien ne respecte les propriétés :)).

Effectivement, en plus d'afficher une fractale d'équation arbitraire spécifiée par l'utilisateur, celui-ci peut spécifier la structure (tenseur métrique et paramétrisation) de l'espace dans lequel cette fractale sera affichée. Évidement, l'utilisateur peut aussi de déplacer et zoomer sur la fractale (soit de manière automatique ou manuellement). Les esthètes confirmés seront ravis de pouvoir pigmenter la fractale de la façon qu'ils le souhaitent.  Tandis que les technologues jouiront de l'affichage (presque) en temps réel grâce à l'utilisation active (OpenGL + shaders) de leur GPU. 

# Fonctionnement
## Mécanismes internes
Pour afficher une fractale en haute définition, il faut, sur chaque pixel de l’écran, itérer l'équation de la fractale et évaluer si cette suite tend à diverger (après environ 600 itérations on peut approximativement considérer que si elle ne diverge pas maintenant, elle ne le fera pas). Donc, en 1080p, ça commence à être lourd. Le progrès technologique étant ce qu'il est, une bonne partie des ordinateurs relativement modernes sont dotés de carte graphique accélérant beaucoup ce genre de calcul. 
Mais la seule manière de fournir les équations nécessaires au shaders est de les écrire directement dans leur fichier. Oui, sur le CPU cela aurai été évité, mais au prix de la perte du temps réel lors de l'affichage. Tandis que notre approche nous rajoute un délai de compilation des shaders (les shaders étant assez petit, c'est négligeable), mais nous garde en temps réel ailleurs.   
D'où il est **impératif** que les fichiers *genericShaderFrag.glsl*, *genericMat.j3md*, *initialFrag.glsl* et *initialMat.j3md* restent dans **/src/vue**. De plus, les fichiers *.j3md*  doivent rester inchanger, tandis que les fichiers *.glsl* doivent garder les commentaires qui indiquent la position des équations à inscrire, en plus de ne pas déranger le code des méthodes de lesquels ils sont contenus. Tout le reste peut être modifié sans altérer le fonctionnement de l'affichage :) (Nous recommandons de ne pas changer les systèmes de coordonnées dans les shaders, c-à-d les normalisations des variables d'entrées, cela rend la translation, le zoom vraiment inconfortable d'utilisation).
## Fonctionnement Mathématique
Traditionnellement, les fractales "euclidiennes" sont évaluées pour chaque point de $\mathbb{R}^2$ (Si on les considère comme des espaces vectoriels, on a que $\mathbb{R}^2 \cong \mathbb{C}$, d'où on utilise un point de $\mathbb{R}^2$ plutôt que de $\mathbb{C}$). Ici, plutôt de d'itérer sur l'espace euclidien, on itère la fractale sur un point d'une variété $\mathcal{M}$. Notons la fonction d'itération de la fractale    
$$
\begin{array}{}
          f:\mathbb{R}^2 \to \mathbb{N} \\
	    \;\;\;\;\;\;\;\;\;\;\;\;\;\;\;\;\;\! x \mapsto \lim\limits_{z\to \infty}f_x(z)
          \end{array}
$$
L'utilisateur nous fournit $\Phi \circ p^{-1}: \mathbb{R}^2 \to \mathbb{R}^3$ (qui est une correspondance $\Phi$ entre $\mathcal{U} \subset \mathcal{M}$ et $\mathbb{R}^3$ composé avec l'inverse de la  paramétrisation $p : \mathcal{U} \subset \mathcal{M} \to \mathbb{R}^2$) et, avec la projection orthogonale $\mathcal{o} :\mathbb{R}^3 \to \mathbb{R}^2$, on peut obtenir l'équation de la fractale
$$
\begin{array}{}
          f':\mathbb{R}^2 \to \mathbb{N} \\
	    \qquad\qquad\qquad \qquad \qquad \; \; \; \; x \mapsto \lim\limits_{z\to \infty}(f_x\circ \mathcal{o} \circ \Phi \circ p^{-1})(z)
          \end{array}
$$
qui prend en compte la variété.

Pour visualiser l'effet de la métrique, on déplace le point, après la projection orthogonale, par la projection orthogonale d'un vecteur normal (dont la longueur est normalisé en fonction de la métrique $\mathcal{g}$, donnée par l'utilisateur) à la variété.
## Bibliothèques logicielles utilisées
-[exp4j](https://github.com/fasseg/exp4j)  (Le dire aux dev);
-[Jmonkey](https://github.com/jMonkeyEngine/jmonkeyengine);
-[JME3-JFX](https://github.com/empirephoenix/JME3-JFX);
-etc.

## Interactions avec le programme 
### Équation de la fractale
Équation vectorielle en fonction de *z* **et** de *c*, où *z* est le paramètre d'itération et *c* la valeur initiale (la position). 
Elle est écrite dans la syntaxe de glsl, donc amusez-vous à injecter le code que vous voulez! (Il faut avant utiliser les variables c et z, mettre un **;**, écrivez votre code (sans **;** à la fin) et voila!)
### Tenseur Métrique
Composantes en fonction de **x** et/ou de **y**. Un nombre est valide aussi. La fonction prend ses valeurs dans [0,1]x[0,1].
### Paramétrisation
Même chose que le tenseur métrique, mais en fonction de **u** et de **v**.
### Couleurs
Trivial.
### Zoom automatique
Trivial.
### Mode de Débogage
Trivial, mais intéressant puisqu'il permet de voir la "forme".  



## À propos de nous 
### [Jackowol](https://github.com/Jackowol)
### [LudoDm](https://github.com/LudoDm)
### [nalf3in](https://github.com/nalf3in)
### [ThatGuyLary](https://github.com/ThatGuyLary)
