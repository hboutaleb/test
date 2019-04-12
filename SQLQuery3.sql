CREATE TABLE [dbo].[Panier]
(
	[Id] INT NOT NULL PRIMARY KEY, 
    [IdBook] VARCHAR(50) NULL, 
    [NombreArticles] INT NULL, 
    [Total] INT NULL DEFAULT 0
)
