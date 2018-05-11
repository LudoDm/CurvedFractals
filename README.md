# CurvedFractals
https://stackedit.io/app#
![enter image description here](https://raw.githubusercontent.com/LudoDm/CurvedFractals/master/res/images/demo.png)
CurvedFractals est un logiciel libre et ouvert, développé dans le cadre du projet d'intégration du DEC en Sciences Informatiques et Mathématiques. 
Ce logiciel, ultime expérience esthétique, prétend humblement permettre de visualiser l’interaction entre deux objets mathématiques, soit les fractales et les variétés riemanniennes (au sens *très* faible du terme: la responsabilité de doter la variété d'une métrique **riemannienne** est laissé à l'utilisateur, ainsi que celle de s'assurer que la paramétrisation qu'il fournit donne effectivement une variété différentielle, mais le résultat s'affiche même si rien ne respecte les propriétés :)).

Effectivement, en plus d'afficher une fractale d'équation arbitraire spécifiée par l'utilisateur, celui-ci peut spécifier la structure (tenseur métrique et paramétrisation) de l'espace dans lequel cette fractale sera affichée. Évidement, l'utilisateur peut aussi de déplacer et zoomer sur la fractale (soit de manière automatique ou manuellement). Les esthètes confirmés seront ravis de pouvoir pigmenter la fractale de la façon qu'ils le souhaitent.  Tandis que les technologues jouiront de l'affichage (presque) en temps réel grâce à l'utilisation active (OpenGL + shaders) de leur GPU. 

# Fonctionnement
## Mécanismes internes

(pourquoi on ecrit dans les shaders et quels fichiers doivent absolument rester intouchés)
## Fonctionnement Mathématique
(Pour param: M->R3 -> fractal(R2) )
(pour metrique: fractal(R2 + proj (n(g)))) ) 
## Librairies utilisées

[exp4j](https://github.com/fasseg/exp4j)(Le dire aux dev), [Jmonkey](https://github.com/jMonkeyEngine/jmonkeyengine), [JME3-JFX](https://github.com/empirephoenix/JME3-JFX), etc.

## Interactions avec le programme 
### Équation de la fractale
### Tenseur Métrique
### Paramétrisation
### Couleurs
###  Zoom automatique
### Mode de Débogage




