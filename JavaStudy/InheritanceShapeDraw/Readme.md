# ShapeDraw 프로젝트 보고서

## 과제 개요
기존 '과제4'의 ShapeDraw는 각 도형의 클래스가 공통으로 가지는 SuperClass가 없었기에, 도형의 종류수 만큼 배열을 선언해야하는 문제점이 있었습니다. 따라서 본 과제는 Inheritance기능을 사용하여 기존의 코드를 재구축하였습니다. 따라서 본 코드는 상속을 통한 '다형성(Polymorphism)'을 확보함으로써, 하나의 Shape 배열만으로 모든 하위 도형 객체를 관리할 수 있게 되어 코드의 유지보수성과 결합도가 개선되었습니다.

## 클래스 상속 관계 및 다이어그램
본 코드에선 Shape라는 SuperClass와 5개의 SubClass가 상속관계를 이루고 있습니다. 아래의 클래스 다이어그램을 참고 부탁드립니다.

```mermaid
classDiagram
    class Main_geometry {
        +String QUIT$
        +printMenu() void$
        +main(String[]) void$
    }

    class Dtool {
        +point(char) void$
        +ln() void$
        +blank() void$
    }

    class Shape {
        #int Shapetype
        #int id
        #int length
        #int length2
        #char pattern
        +Shape()
        +Shape(int, int, char)
        +Shape(int, int, int, char)
        +toString() String
        +draw() void*
        +getPattern() char
        +setPattern(char) void
        +getId() int
        +setId(int) void
        +getlength() int
        +setlength(int) void
    }

    class Circle {
        -int radius
        +Circle()
        +Circle(int, int, char)
        +draw() void
    }

    class Diamond {
        +Diamond()
        +Diamond(int, int, char)
        +draw() void
    }

    class Hourglass {
        +Hourglass()
        +Hourglass(int, int, char)
        +draw() void
    }

    class Rectangle {
        +Rectangle()
        +Rectangle(int, int, int, char)
        +draw() void
    }

    class Triangle {
        +Triangle()
        +Triangle(int, int, char)
        +draw() void
    }

    Shape <|-- Circle
    Shape <|-- Diamond
    Shape <|-- Hourglass
    Shape <|-- Rectangle
    Shape <|-- Triangle
```


## 유틸리티 클래스 (Dtool) 분석
유틸리티 클래스 'Dtool'에는 오직 static method만 존재합니다. 이는 5개의 도형 SubClass들이 공통적으로 draw()를 구현하는데 필요한 기능들을 담아두는 역할입니다.
ex) 점 찍기, 줄바꿈, 공백 출력

## matches 메서드를 활용한 코드 간결화
본 프로그램에서는 초기 입력값을 String형태로 받습니다. 그 이유는 특정 도형의 출력 로그를 보려면 '도형 타입' + 'log' 라는 키워드를 입력해야 하기 때문입니다. 초기 코드에선 이를 존재 가능한 모든 경우의 수를 if문에 작성해두고 입력값과 비교하는 형식으로 이루어졌습니다. 다만 이는 확장성이 매우 떨어지기에, 정규표현식(regex)과 matches 메서드를 이용하여 코드를 매우 간결화 했습니다. 또한 이를 위하여 각 SubClass들에게 고유의 int값을 부여하여 객체의 종류를 보다 쉽게 판별하게 하였습니다.

## 부모 클래스 기반 생성자 설계
Shape Class에는 3가지 생성자가 있습니다. 첫번째는 Default 생성자, 그리고 나머지 두개는 각각 파라미터 3개,4개를 요구하는 생성자입니다. 이는 SubClass에서 'super' 키워드를 통하여 상위 생성자를 찾을때, 가능한 모든 파라미터 종류를 미리 선언해둔 것입니다.

## 사용자 입력 예외 처리
기존 코드의 취약점이었던 예외처리 (음수값, 0 입력)에 대한 방어 코드를 보강했습니다.

## shapetype 변수와 instanceof 연산자 방식 고찰
본 코드는 instanceof 연산자를 사용하지 않았습니다. 그 이유는 먼저 instanceof를 사용할 시에는 모든 클래스에 대한 case를 만들어두어야 하지만, shapetype이란 int값으로 쉽게 객체의 종류를 판별 할 수 있으면 코드가 더욱 간결화되기 때문입니다. (위의 matches 관련 기술 참고) 다만, 이러한 방식에도 한계점이 있습니다. 'int shapetype'은 protected로 선언되어있는데, 이는 같은 패키지내에서 얼마든지 접근이 가능하기에 보안 측면에서는 매우 취약하다고 볼 수 있습니다. 각 두가지 방식에 대한 한계점을 잘 고민하여 더욱 뛰어난 방법을 고안해내는 것을 목표로 삼겠습니다.





*본 문서는 자바 프로그래밍 과제 제출 및 GitHub 포트폴리오를 목적으로 작성되었습니다.*
---