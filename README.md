# Kotlin-Game-Engine-2D-by-wRadchuk

## О проекте

Этот проект - 2D игровой движок, разработанный на языке Kotlin. Он предназначен для создания игр на платформе Android. Движок предоставляет набор инструментов для разработки и управления игровым процессом, включая рисование на холсте, обработку ввода пользователя и управление игровой логикой.

## Основные возможности

- [GameLevel](game_engine/src/main/java/com/wradchuk/game_engine/components/GameLevel.kt) - создание ортогональныx 2D уровней на основе карты плиток
- [Camera](game_engine/src/main/java/com/wradchuk/game_engine/objects/Camera.kt) - камера для перемещения по игровому пространству
- [Joystick](game_engine/src/main/java/com/wradchuk/game_engine/core/Joystick.kt) - обрабатывает события MotionEvent для перемещения камеры в пространстве
- [GameViewport](game_engine/src/main/java/com/wradchuk/game_engine/core/GameViewport.kt) - игровое отображение, управление камерой и установка её по центру игровой сцены
- [GameScreen](game_engine/src/main/java/com/wradchuk/game_engine/components/GameScreen.kt) - представляет экран игры, на котором уже можно создавать игру используя движок

## Установка

1. Клонируйте репозиторий на локальную машину.
2. Откройте проект в Android Studio Jellyfish или в верисии новее.
3. Ознакомьтесь с кодом примера игры в [MainActivity](app/src/main/java/com/wradchuk/kotlingameengine2d/MainActivity.kt).

## Использование

TODO 

## Примеры

TODO

## Вклад

Спасибо за ваш интерес к проекту! Если у вас есть предложения или исправления, пожалуйста, откройте issue или pull request.

## Лицензия

Этот проект лицензируется в соответствии с условиями лицензии [Apache License 2.0](LICENSE).

