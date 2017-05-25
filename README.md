# ToDo API

レイヤードアーキテクチャ + DDDのサンプルウェブAPIです。

## API一覧

### CreateTask

指定したタイトルでタスクを作ります。

```
POST /CreateTask HTTP/1.1
Content-Type: application/json
...

{"title": "task title"}
```

### FinishTask

対象のタスクを終了します。

```
POST /FinishTask HTTP/1.1
Content-Type: appliation/json
...

{"id": "TASK_ID_XXX..."}
```

### GetTaskList

すべてのタスクの一覧を取得します。並びは作成日時の降順です。

```
GET /GetTaskList HTTP/1.1
...
```

### GetUnfinishedTaskList

まだ終了していないタスクの一覧を取得します。並びは作成日時の降順です。

```
GET /GetUnfinishedTaskList HTTP/1.1
...
```

### GetTaskStatistics

タスクの統計を取得します。

```
GET /GetTaskStatistics HTTP/1.1
...
```

## インストール

事前に必要なものは:

- sbt
- PostgreSQL （UUIDを扱えるRDBなら何でも良いとは思いますが、動作未確認）

です。

### データベース/ユーザを作成

```
$ createdb todo
$ createuser todo
```

### テーブルを用意

```
$ psql -d todo
> CREATE EXTENSION "pgcrypto";
> CREATE TABLE tasks (id UUID NOT NULL PRIMARY KEY, title VARCHAR(20) NOT NULL, created_at TIMESTAMP NOT NULL, finished_at TIMESTAMP);
```

### データベース接続情報を設定

`app/src/main/resources/database.conf` を下記の内容で作成する。

```
db.default.driver   = "org.postgresql.Driver"
db.default.url      = "jdbc:postgresql://localhost:5432/todo"
db.default.username = "todo"
db.default.password = ""
```

## 実行

```
$ sbt
> app/jetty:start
```

## テスト

インストールの手順に従い、テスト用のデータベース（例えば todo_test）を作成。`app/src/test/resources/database.conf` を下記の内容で作成する。

```
db.default.driver   = "org.postgresql.Driver"
db.default.url      = "jdbc:postgresql://localhost:5432/todo_test"
db.default.username = "todo"
db.default.password = ""
```

下記コマンドでテストを実行。

```
$ sbt
> test
```
